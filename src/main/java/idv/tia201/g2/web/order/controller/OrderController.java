package idv.tia201.g2.web.order.controller;

import java.util.List;
import idv.tia201.g2.core.pojo.Core;
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

    // 後台 顯示列表
    @GetMapping("manage")
    public List<Orders> manage() {
        return orderService.findAll();
    }

    // 後台 顯示明細
    @GetMapping("manage/{orderId}")
    public List<OrderDetail> detail(@PathVariable Integer orderId) {
        return orderService.findByOrderId(orderId);
    }

    // 後台 修改明細
    @PutMapping("manage/{orderId}")
    public Orders update(
            @PathVariable Integer orderId,
            @RequestBody Orders reqOrders
    ) {
        reqOrders.setOrderId(orderId);
        return orderService.updateStatus(reqOrders);
    }



    //todo
    //    public OrderDto detail(@PathVariable Integer orderId) {
//        return orderService.findByOrderId(orderId);
//    }

    // --------------前台-----------------------------
    // 前台 顯示列表
    @GetMapping("member/{customerId}")
    public List<Object[]> memberOrder(@PathVariable Integer customerId) {
//        return orderService.findByCustomerId(customerId);
        return null;
    }

    // 前台 顯示明細
    @GetMapping("/member/detail/{orderId}")
    public Orders memberDetail(@PathVariable Integer orderId) {
//        return orderService.findByOrderId(orderId);
        return null;
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
