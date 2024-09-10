package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order/manage")
public class orderController {

    @Autowired
    private OrderService orderService;

    // todo

    // 後台 顯示列表
    @GetMapping
    public List<Orders> manage() {
        return orderService.findAll();
    }

    // 後台 顯示明細
    @GetMapping("detail/{orderId}")
    public Orders detail(@PathVariable Integer orderId) {
        return orderService.findByOrderId(orderId);
    }

    // 後台 修改明細資料
    @PutMapping
    public Core update(@RequestBody Orders orders) {

        return orderService.updateStatus(orders);
    }


}
