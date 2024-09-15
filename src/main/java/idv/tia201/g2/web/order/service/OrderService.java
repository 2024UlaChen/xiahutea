package idv.tia201.g2.web.order.service;

import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import java.util.List;

public interface OrderService {

    List<Orders> findAll();
    List<OrderDetail> findByOrderId(int orderId);

    List<OrderDto> findByCustomerId(int customerId);

    OrderDto addOrder(OrderDto orderDto);


    Orders addOrder(Orders order);

    Orders addStar(Orders order);
    Orders updateStatus(Orders order);
}
