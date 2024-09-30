package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.service.InvoiceService;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // -------- FINISH ---------------------------------
    // 後端 接收發票回傳參數
    @PostMapping("/issueInvoice")
    public ResponseEntity<String> issueInvoice(@RequestBody Orders order) {
        String invoiceNo = invoiceService.createInvoice(order);
        if (invoiceNo != null) {
            return ResponseEntity.ok(invoiceNo);
        } else {
            return ResponseEntity.status(500).body("發票回傳錯誤");
        }
    }
}