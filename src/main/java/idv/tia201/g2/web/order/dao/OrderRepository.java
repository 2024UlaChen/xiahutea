package idv.tia201.g2.web.order.dao;

import java.sql.Timestamp;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    // 前台 訂單列表 顯示
    @Query("FROM Orders o WHERE o.customerId = :customerId")
    Page<Orders> findByCustomerId(@Param("customerId") int customerId, Pageable pageable);

    Page<Orders> findByCustomerIdAndOrderCreateDatetimeBetween(Integer customerId, Timestamp orderCreateDatetime, Timestamp orderCreateDatetime2, Pageable pageable);

    // 後台 訂單列表 顯示 & 依條件查詢
    @Query("SELECT o FROM Orders o " +
            "JOIN o.store s " +
            "JOIN o.customer c " +
            "WHERE (:orderId IS NULL OR o.orderId = :orderId) " +
            "AND (:storeId IS NULL OR o.store.storeId = :storeId) " +
            "AND (:storeName IS NULL OR s.storeName LIKE %:storeName%) " +
            "AND (:memberNickname IS NULL OR c.nickname LIKE %:memberNickname%) " +
            "AND (:orderStatus IS NULL OR o.orderStatus = :orderStatus) " +
            "AND (:dateStart IS NULL OR o.orderCreateDatetime >= :dateStart) " +
            "AND (:dateEnd IS NULL OR o.orderCreateDatetime <= :dateEnd)")
    Page<Orders> findByCriteria(
            @Param("orderId") Integer orderId,
            @Param("storeId") Integer storeId,
            @Param("storeName") String storeName,
            @Param("memberNickname") String memberNickname,
            @Param("orderStatus") Integer orderStatus,
            @Param("dateStart") Timestamp dateStart,
            @Param("dateEnd") Timestamp dateEnd,
            Pageable pageable
    );

}