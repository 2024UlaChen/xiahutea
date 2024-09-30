package idv.tia201.g2.web.cart.controller;

import ecpay.payment.integration.ecpayOperator.EcpayFunction;
import idv.tia201.g2.web.cart.service.EcPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class EcPayController {

    @Autowired
    EcPayService ecPayService;

    @PostMapping("/payment/createorder")
    public ResponseEntity<String> createOrder(@RequestParam("TotalAmount") Integer totalAmount) {
        // 生成綠界支付表單
        String aioCheckOutALLForm = ecPayService.ecpayCheckout(totalAmount);
        // 返回支付表單
        return ResponseEntity.ok(aioCheckOutALLForm);
    }

    @PostMapping("/result")
    public String handlePaymentResult(@RequestParam Map<String, String> allParams) {

        // 列印所有參數，確認收到的資料
        allParams.forEach((key, value) -> System.out.println(key + ": " + value));

        // 根據 RtnCode及交易編號 判斷交易結果
        String rtnCode = allParams.get("RtnCode");
        if ("1".equals(rtnCode)) {
            return "redirect:/paysuccess.html";
        } else {
            return "redirect:/payfail.html";
        }
//        System.out.println("MerchantID:"+MerchantID);
//        System.out.println("MerchantTradeNo:"+MerchantTradeNo);
//        System.out.println("PaymentDate:"+PaymentDate);
//        // 根據 RtnCode 判斷交易結果
//        String message;
//        if ("1".equals(RtnCode)) { // 通常 "1" 代表成功
////            message = "交易成功！訂單編號："; // 這裡可以添加訂單編號
//            return "redirect:/paysuccess.html";
//        } else {
////            message = "交易失敗！原因："; // 可以添加失敗原因
//            return "redirect:/payfail.html";
//        }
//
//        // 將結果添加到模型中
////        model.addAttribute("message", message);
    }
}
