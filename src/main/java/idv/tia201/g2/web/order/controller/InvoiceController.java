package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    private static final String API_URL = "https://einvoice-stage.ecpay.com.tw/B2CInvoice/Issue";

    // TODO 發票
    @PostMapping(API_URL)
    public String getInvoice(String params) {
        return invoiceService.getInvoiceInfo(params);
    }


}