package idv.tia201.g2.web.order.service;

import java.sql.Timestamp;
import java.time.LocalDate;
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

    List<OrderDetail> findByOrderId(Integer orderId);

    Page<OrderDto> findByCustomerId(Integer customerId, Pageable pageable);
    Page<OrderDto> findByCustomerIdAndDateRange(Integer customerId, Timestamp dateStart, Timestamp dateEnd, Pageable pageable);

    OrderDto findByMemberOrderId(Integer orderId);

    // 後台 訂單列表 顯示 & 依條件查詢
    Page<Orders> findByCriteria(
            Integer orderId, Integer storeId, String storeName, String memberNickname,
            Integer orderStatus, LocalDate dateStart, LocalDate dateEnd, Integer page, Integer size
    );

}