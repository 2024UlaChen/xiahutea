package idv.tia201.g2.web.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import idv.tia201.g2.web.order.util.InvoiceUtil;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
public class InvoiceService {

    private static final String MerchantID = "2000132";

    @Autowired
    private OrderService orderService;


    public Map<String, Object> setInvoiceInfo(Orders order) {
        // 設定傳入訂單的發票參數
        String donation = "0";             // 捐贈參數
        String print = "0";                //列印參數
        String carrierType = "";          //載具類別
        String carrierNum = "";

        // case 1 : 電子發票-手機載具
        if(order.getInvoiceMethod() == 3){
            carrierType = "3";
            carrierNum = order.getInvoiceCarrier();
        }
        // case 2 : 電子發票-綠界會員
        if(order.getInvoiceMethod() == 2){
            carrierType = "1";
            carrierNum = "";
        }
        // case 3 : 電子發票-統一發票
        if(order.getInvoiceMethod() == 2){
            carrierType = "";
            carrierNum = "";
        }

        // RqHeader參數
        Map<String, Object> rqHeader = new LinkedHashMap<>(); //有序的MAP
        rqHeader.put("Timestamp", String.valueOf(new Timestamp(System.currentTimeMillis())));

        // Items參數
        Map<String, Object> items = new LinkedHashMap<>();
        items.put("ItemName", "平台處理費");
        items.put("ItemCount", 1);
        items.put("ItemWord", "筆");
        items.put("ItemPrice", 10);
        items.put("ItemAmount", 10);

        // Data參數
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("MerchantID", MerchantID);
        data.put("RelateNumber", String.valueOf(order.getOrderId()));
        data.put("CustomerID ", String.valueOf(order.getCustomerId()));
        data.put("CustomerIdentifier", String.valueOf(order.getInvoiceVat()));
        data.put("CustomerName", order.getCustomer().getNickname());
        data.put("CustomerAddr", order.getReceiverAddress());
        data.put("CustomerPhone", order.getCustomer().getCustomerPhone());
        data.put("CustomerEmail", order.getCustomer().getCustomerEmail());
        data.put("ClearanceMark", "1");
        data.put("Print", print);
        data.put("Donation", donation);
        data.put("CarrierType", carrierType);
        data.put("CarrierNum", carrierNum);
        data.put("TaxType", "1");
        data.put("SalesAmount", 10);
        data.put("Items", items);
        data.put("InvType", "07");

        // 1. Map > JSON字串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 2. JSON字串 > URL編碼大寫 > AES加密。
        String encryptedData = InvoiceUtil.encryptAES(jsonData);

        // 回傳參數
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("MerchantID", MerchantID);
        params.put("RqHeader", rqHeader);
        params.put("Data", encryptedData);

        return params;
    }

//----------------------------------------------------------------
    // 回傳參數
    //    {
    //         "MerchantID": "2000132",
    //         "RpHeader": {
    //            "Timestamp": 1525169058
    //         },
    //         "TransCode": 1,
    //         "TransMsg": "",
    //         "Data": "加密資料"
    //     }
    //          Data回傳參數
    //                 "RtnCode": 1,
    //                 "RtnMsg": "開立發票成功",
    //                 "InvoiceNo": "UV11100012",
    //                 "InvoiceDate": "2019-09-17 17:17:31",
    //                 "RandomNumber": "6866"

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
        String jsonData = InvoiceUtil.decryptAES(encryptedData);
        // JSON > 物件
        JsonNode data = null;
        try {
            data = objectMapper.readTree(jsonData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 取出屬性
        String rtnMsg = data.get("RtnMsg").asText();
        String invoiceNo = data.get("InvoiceNo").asText();
        if(!(rtnMsg.equals("開立發票成功"))){
            return "開立發票失敗";
        }
        return invoiceNo;
    }
}