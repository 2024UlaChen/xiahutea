package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.vo.Orders;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order/manage")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 後台 顯示列表
    @GetMapping
    public List<Orders> manage() {
        return orderService.findAll();
    }

    // 後台 顯示明細
    @GetMapping("{orderId}")
    public Orders detail(@PathVariable Integer orderId) {
        return orderService.findByOrderId(orderId);
    }

    // 後台 修改明細資料
    @PutMapping("{orderId}")
    public Orders update(
            @PathVariable Integer orderId,
            @RequestBody Orders reqOrders
    ) {
        reqOrders.setOrderId(orderId);
        return orderService.updateStatus(reqOrders);
    }

    // todo 新增資料
    @PostMapping
    public Core addNewOrder(@RequestBody Orders orders) {
        return  orderService.addOrder(orders);
    }

    // 新增評分
    @PutMapping("star/{orderId}")
    public Orders addNewStar(
            @PathVariable Integer orderId,
            @RequestBody Orders reqOrders
    ) {
        reqOrders.setOrderId(orderId);
        return orderService.addStar(reqOrders);
    }

}
