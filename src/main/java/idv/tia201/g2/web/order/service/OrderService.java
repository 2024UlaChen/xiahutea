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

    // 前台 新增資料
    OrderDto addOrder(Orders order, List<OrderDetail> orderDetail);

    // 前台 訂單列表 顯示 全訂單
    Page<OrderDto> findByCustomerId(Integer customerId, Pageable pageable);

    // 前台 訂單列表 顯示 篩選日期
    Page<OrderDto> findByCustomerIdAndDateRange(Integer customerId, Timestamp dateStart, Timestamp dateEnd, Pageable pageable);

    // 前台 訂單明細 顯示
    OrderDto findByMemberOrderId(Integer orderId);

    // 前台 訂單明細 新增評分
    Orders addStar(Orders order);


    // 後台 訂單列表 顯示 & 依條件查詢
    Page<Orders> findByCriteria(
            Integer orderId, Integer storeId, String storeName, String memberNickname,
            Integer orderStatus, LocalDate dateStart, LocalDate dateEnd, Integer page, Integer size
    );

    // 後台 訂單明細 顯示
    List<OrderDetail> findByOrderId(Integer orderId);

    // 後台 訂單明細 修改
    Orders updateStatus(Orders order);

}