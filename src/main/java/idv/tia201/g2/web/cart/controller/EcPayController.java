//package idv.tia201.g2.web.cart.controller;
//
//
//import idv.tia201.g2.web.cart.service.EcPayService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/payment")
//public class EcPayController {
//    @Autowired
//    EcPayService ecPayService;
//
//    @PostMapping("/createorder")
//    public String createOrder(){
//        String aioCheckOutALLForm = ecPayService.ecpayCheckout();
//        return aioCheckOutALLForm;
//    }
//
//
////    @PostMapping("/return")
////    public String handleECPayReturn(@RequestParam Map<String, String> paymentResult) {
////        // 步驟 1：檢查 CheckMacValue 是否正確，確保資料未被篡改
////        Hashtable<String, String> params = new Hashtable<>(paymentResult);
////        boolean isValid = aio.compareCheckMacValue(params);
////        if (isValid) {
////            // 步驟 2：檢查交易狀態
////            String rtnCode = paymentResult.get("RtnCode");
////            if ("1".equals(rtnCode)) { // 綠界的 RtnCode 為 1 表示交易成功
////                // 步驟 3：更新訂單狀態，例如將該訂單標記為已付款
////                String merchantTradeNo = paymentResult.get("MerchantTradeNo");
////                String tradeNo = paymentResult.get("TradeNo"); // 綠界的交易編號
////
////                // TODO: 更新資料庫中的訂單狀態，將該訂單標記為已付款
////
////                // 步驟 4：回應綠界系統，通知接收成功
////                return "1|OK";
////            } else {
////                // TODO: 處理付款失敗的情況
////                return "0|FAIL";
////            }
////        } else {
////            // 如果 CheckMacValue 檢查失敗，代表資料可能被篡改
////            return "0|FAIL";
////        }
////    }
//
//}
