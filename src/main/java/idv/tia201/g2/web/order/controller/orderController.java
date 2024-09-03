package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("order")
public class orderController {

    private OrderService orderService;

    @GetMapping("list")
    public List<Orders> manage(Model model){

    }


}
