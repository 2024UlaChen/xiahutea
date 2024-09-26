package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.service.InvoiceService;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // TODO 發票
    @PostMapping
    public String invoice(Orders order) {
        return invoiceService.submitInvoice(order);
    }
}