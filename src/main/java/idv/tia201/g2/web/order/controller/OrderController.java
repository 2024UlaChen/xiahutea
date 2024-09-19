package idv.tia201.g2.web.order.controller;

import java.util.List;
import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // FINISH
    // 後台 訂單列表 顯示
    @GetMapping("manage")
    public List<Orders> manage() {
        return orderService.findAll();
    }

    // 後台 訂單明細 顯示
    @GetMapping("manage/{orderId}")
    public List<OrderDetail> detail(@PathVariable Integer orderId) {
        return orderService.findByOrderId(orderId);
    }

    // 後台 訂單明細 修改
    @PutMapping("manage/{orderId}")
    public Orders save(
            @PathVariable Integer orderId,
            @RequestBody Orders reqOrders
    ) {
        reqOrders.setOrderId(orderId);
        return orderService.updateStatus(reqOrders);
    }

    // ------------------------------------------------------
    // --------------前台-----------------------------
    //todo

    // 前台 訂單列表 顯示
    @GetMapping("member/{customerId}")
    public List<OrderDto> memberOrder(@PathVariable Integer customerId) {
        return orderService.findByCustomerId(customerId);
    }

    // 前台 訂單明細 顯示
    @GetMapping("member/detail/{orderId}")
    public OrderDto memberDetail(@PathVariable Integer orderId) {
        return orderService.findByMemberOrderId(orderId);
    }



    // todo 前台 新增資料
    @PostMapping("member")
    public Core addNewOrder(@RequestBody Orders orders) {
//        return  orderService.addOrder(orders);
        return null;
    }

    // 新增評分
    @PutMapping("member/star/{orderId}")
    public Orders addNewStar(
            @PathVariable Integer orderId,
            @RequestBody Orders reqOrders
    ) {
        reqOrders.setOrderId(orderId);
        return orderService.addStar(reqOrders);
    }

}
