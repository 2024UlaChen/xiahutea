package idv.tia201.g2.web.order.service;

import java.util.List;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;

public interface OrderService {

    Orders addOrder(Orders order);

    Orders addStar(Orders order);
    Orders updateStatus(Orders order);

    List<Orders> findAll();
    List<OrderDetail> findByOrderId(int orderId);
    List<OrderDto> findByCustomerId(int customerId);
    OrderDto findByMemberOrderId(int orderId);
}
