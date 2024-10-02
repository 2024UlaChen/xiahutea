package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    // 後台 訂單列表 顯示
    @Query("FROM Orders")
    Page<Orders> findAll(Pageable pageable);

    // 前台 訂單列表 顯示
    @Query("FROM Orders o WHERE o.customerId = :customerId")
    Page<Orders> findByCustomerId(@Param("customerId") int customerId, Pageable pageable);

    Page<Orders> findByCustomerIdAndOrderStatusAndOrderCreateDatetimeBetween(Integer customerId, Integer orderStatus, Timestamp orderCreateDatetime, Timestamp orderCreateDatetime2, Pageable pageable);
    Page<Orders> findByCustomerIdAndOrderCreateDatetimeBetween(Integer customerId, Timestamp orderCreateDatetime, Timestamp orderCreateDatetime2, Pageable pageable);
}
