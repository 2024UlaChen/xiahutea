package idv.tia201.g2.web.order.service;

import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class InvoiceService {

    private static final String API_URL = "https://einvoice-stage.ecpay.com.tw/B2CInvoice/Issue";
    private static final String PlatformID = "";
    private static final String MerchantID = "2000132";
    private static final String HashKey = "ejCk326UnaZWKisg";
    private static final String HashIV = "q9jcZX8Ib9LM8wYk";


    // todo
    // 發票api
    // 請將要加密資料先做URL Encode編碼，再進行AES加密。

    public String submitInvoice(Orders order) {

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
        Map<String, Object> rqHeader = new HashMap<>();
        rqHeader.put("Timestamp", String.valueOf(new Timestamp(System.currentTimeMillis())));

        // Items參數
        Map<String, Object> items = new HashMap<>();
        items.put("ItemName", "平台處理費");
        items.put("ItemCount", 1);
        items.put("ItemWord", "筆");
        items.put("ItemPrice", 10);
        items.put("ItemAmount", 10);

        // Data參數說明
        Map<String, Object> data = new HashMap<>();
        data.put("MerchantID", MerchantID);
        data.put("RelateNumber", String.valueOf(order.getOrderId()));
        data.put("CustomerID ", String.valueOf(order.getCustomerId()));
        data.put("CustomerIdentifier", String.valueOf(order.getInvoiceVat()));
        data.put("CustomerName", order.getCustomer().getNickname());
        data.put("CustomerAddr", order.getReceiverAddress());
        data.put("CustomerPhone", order.getCustomer().getCustomerPhone().toString());
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

        // 回傳參數
        Map<String, Object> params = new HashMap<>();
        params.put("MerchantID", MerchantID);
        params.put("RqHeader", rqHeader);
        params.put("Data", data);

        System.out.println(params);
        return null;
    }



}