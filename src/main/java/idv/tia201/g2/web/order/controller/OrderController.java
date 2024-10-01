package idv.tia201.g2.web.order.controller;

import java.util.ArrayList;
import java.util.List;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // -------- FINISH ---------------------------------
    // 前台 新增資料
    @PostMapping("addOrder")
    public OrderDto addNewOrder(@RequestBody OrderDto orderDto) {
        return orderService.addOrder(orderDto.getOrders(), orderDto.getOrderDetails());
    }

    // 前台 訂單列表 顯示
    @GetMapping("member/{customerId}")
    public Page<OrderDto> memberOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession httpSession
    ) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        if (totalUserDTO.getUserTypeId() != 0) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "orderCreateDatetime"));
        return orderService.findByCustomerId(totalUserDTO.getUserId(), pageable);
    }

    // 前台 訂單明細 顯示
    @GetMapping("member/detail/{orderId}")
    public OrderDto memberDetail(@PathVariable Integer orderId, HttpSession httpSession) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        OrderDto order = orderService.findByMemberOrderId(orderId);
        if (totalUserDTO.getUserTypeId() != 0 || !(totalUserDTO.getUserId().equals(order.getOrders().getCustomerId()))) {
            OrderDto orderDto = new OrderDto();
            return orderDto;
        }
        return order;
    }

    // 前台 訂單明細 新增評分
    @PutMapping("member/star/{orderId}")
    public Orders addNewStar( @PathVariable Integer orderId, @RequestBody Orders reqOrders) {
        reqOrders.setOrderId(orderId);
        return orderService.addStar(reqOrders);
    }

    // 後台 訂單列表 顯示
    @GetMapping("manage")
    public Page<Orders> findAllByPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "orderCreateDatetime"));
        return orderService.findAll(pageable);
    }

    // 後台 訂單明細 顯示
    @GetMapping("manage/{orderId}")
    public List<OrderDetail> detail(@PathVariable Integer orderId) {
        return orderService.findByOrderId(orderId);
    }

    // 後台 訂單明細 修改
    @PutMapping("manage/{orderId}")
    public Orders save( @PathVariable Integer orderId,@RequestBody Orders reqOrders
    ) {
        reqOrders.setOrderId(orderId);
        return orderService.updateStatus(reqOrders);
    }
}