//package idv.tia201.g2.web.cart.service.impl;
//
//import ecpay.payment.integration.AllInOne;
//import ecpay.payment.integration.domain.AioCheckOutALL;
//import idv.tia201.g2.web.cart.service.EcPayService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EcPayServiceimpl implements EcPayService {
//
//    @Override
//    public String ecpayCheckout() {
//        AllInOne aio = new AllInOne();
//        AioCheckOutALL obj = new AioCheckOutALL();
//
////        obj.setMerchantTradeNo(params.get("xiahutea0001"));
////        obj.setMerchantTradeDate(params.get("MerchantTradeDate")); // 格式: yyyy/MM/dd HH:mm:ss
////        obj.setTotalAmount(params.get("TotalAmount"));
////        obj.setTradeDesc(params.get("TradeDesc"));
////        obj.setItemName(params.get("ItemName"));
////        obj.setReturnURL("http://localhost:8080/cms/ecPayReturn.html");
////        obj.setChooseSubPayment(params.get("ChoosePayment")); // 支付方式
//        obj.setMerchantTradeNo("testCompany0004");
//        obj.setMerchantTradeDate("2024/01/01 08:05:23");
//        obj.setTotalAmount("50");
//        obj.setTradeDesc("test Description");
//        obj.setItemName("TestItem");
//        obj.setReturnURL("http://localhost:8080/cms/ecPayReturn.html");
//        // 產生支付表單
//        String form = aio.aioCheckOut(obj, null);
//        return form;
//    }
//}
