package idv.tia201.g2.web.order.service;

import java.sql.Timestamp;
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

    Page<Orders> findAll(Pageable pageable);
    List<OrderDetail> findByOrderId(int orderId);
    Page<OrderDto> findByCustomerId(int customerId, Pageable pageable);

    OrderDto findByMemberOrderId(int orderId);
    Page<OrderDto> findByCustomerIdAndDateRange(Integer customerId, Integer orderStatus, Timestamp dateStart, Timestamp dateEnd, Pageable pageable);

}