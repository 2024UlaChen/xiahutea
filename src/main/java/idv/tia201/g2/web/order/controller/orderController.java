package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class orderController {

    @Autowired
    private OrderService orderService;

    // 後台 查詢列表
    @GetMapping("list")
    public List<Orders> readAll(){
        return orderService.findAll();
    }

    // 後台 連結明細

}
