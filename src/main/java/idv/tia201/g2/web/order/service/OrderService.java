package idv.tia201.g2.web.order.service;

import java.util.List;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto addOrder(Orders order, List<OrderDetail> orderDetail);
    Orders addStar(Orders order);
    Orders updateStatus(Orders order);

  //  List<Orders> findAll();
    Page<Orders> findAll(Pageable pageable);
    List<OrderDetail> findByOrderId(int orderId);
    List<OrderDto> findByCustomerId(int customerId);
    OrderDto findByMemberOrderId(int orderId);
}