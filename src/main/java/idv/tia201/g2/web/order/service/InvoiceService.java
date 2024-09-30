package idv.tia201.g2.web.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import idv.tia201.g2.web.order.util.InvoiceAesUtil;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Transactional
public class InvoiceService {

    private static final String MerchantID = "2000132";
    private static final String INVOICE_API_URL = "https://einvoice-stage.ecpay.com.tw/B2CInvoice/Issue";

    @Autowired
    private RestTemplate restTemplate;

    // 設定 傳入發票參數
    public Map<String, Object> setInvoiceInfo(Orders order) {

        String carrierType = "";        //載具類別
        String carrierNum = "";

        // case 1 : 電子發票-手機載具
        if(order.getInvoiceMethod() == 1){
            carrierType = "3";
            carrierNum = order.getInvoiceCarrier();
        }
        // case 2 : 電子發票-綠界會員
        if(order.getInvoiceMethod() == 2){
            carrierType = "1";
            carrierNum = "";
        }
        // case 3 : 電子發票-統一發票
        if(order.getInvoiceMethod() == 3){
            carrierType = "1";
            carrierNum = "";
        }

        // 取得當前UTC時間的Instant
        Instant now = Instant.now();
        // 轉換為GMT+8時區的ZonedDateTime
        ZonedDateTime gmtPlus8 = now.atZone(ZoneOffset.ofHours(8));
        // 轉換為Unix時間戳（秒）
        long timestamp = gmtPlus8.toEpochSecond();

        // RqHeader參數
        Map<String, Object> rqHeader = new LinkedHashMap<>(); //有序的MAP
        rqHeader.put("Timestamp", String.valueOf(timestamp));

        // Items參數
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("ItemName", "ServiceFee");
        item.put("ItemCount", 1);
        item.put("ItemWord", "筆");
        item.put("ItemPrice", 10);
        item.put("ItemAmount", 10);
        items.add(item);

        // Data參數
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("MerchantID", MerchantID);
        data.put("RelateNumber", String.valueOf(order.getOrderId()));
        data.put("CustomerID ", String.valueOf(order.getCustomerId()));
        // 檢查是否需要統一編號，invoiceVat 若為 null 或空，則不傳入 CustomerIdentifier
        if (order.getInvoiceVat() != null && !isEmpty(order.getInvoiceVat()) && order.getInvoiceMethod() == 3) {
            data.put("CustomerIdentifier", order.getInvoiceVat());
        } else {
            data.put("CustomerIdentifier", "");
        }
        data.put("CustomerName", "");
        data.put("CustomerPhone", order.getReceiverPhone());
        data.put("Print", "0");
        data.put("Donation", "0");
        data.put("CarrierType", carrierType);
        data.put("CarrierNum", carrierNum);
        data.put("TaxType", "1");
        data.put("SalesAmount", 10);
        data.put("Items", items);
        data.put("InvType", "07");

        // 1. Map > JSON字串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData;
        try {
            jsonData = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 2. JSON字串 > URL編碼大寫 > AES加密
        String encryptedData = InvoiceAesUtil.encryptAES(jsonData);

        // 回傳參數
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("MerchantID", MerchantID);
        params.put("RqHeader", rqHeader);
        params.put("Data", encryptedData);

        return params;
    }

    public String getInvoiceInfo(String params){
        // JSON 轉 物件
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode objectParams;
        try {
            objectParams = objectMapper.readTree(params);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String encryptedData = objectParams.get("Data").asText();
        //  AES解密 > URL編碼大寫 > JSON
        String jsonData = InvoiceAesUtil.decryptAES(encryptedData);
        // 確保解密成功，並且解密後不是空值
        if (jsonData == null || jsonData.isEmpty()) {
            return null;
        }

        // JSON > 物件
        JsonNode data;
        try {
            data = objectMapper.readTree(jsonData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 取出屬性值
        String invoiceNo = data.get("InvoiceNo").asText();
        int rtnCode = data.get("RtnCode").asInt();
        if(rtnCode != 1){
            return null ;
        }
        //回傳發票號碼
        return invoiceNo;
    }

    public String createInvoice(Orders order) {
        Map<String, Object> params = setInvoiceInfo(order);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonParams;
        try {
            jsonParams = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 傳送表頭格式與參數
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonParams, headers);

        // 綠界 API
        ResponseEntity<String> response = restTemplate.exchange(INVOICE_API_URL, HttpMethod.POST, requestEntity, String.class);

        // 回傳結果
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            System.out.println("----------Response Body: " + responseBody);
            return getInvoiceInfo(responseBody); // 如果成功 回傳發票號碼
        } else {
//            System.out.println("----------Error: " + response.getStatusCode() + " - " + response.getBody());
            return null;
        }
    }
}