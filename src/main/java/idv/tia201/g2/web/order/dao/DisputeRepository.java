package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.sql.Timestamp;

public interface DisputeRepository extends JpaRepository<DisputeOrder,Integer > {


//    @Query("FROM DisputeOrder")
//    Page<DisputeOrder> findAll(Pageable pageable);

    // 後台 爭議列表 顯示 & 依條件查詢
    @Query("SELECT d FROM DisputeOrder d " +
            "JOIN d.order o " +
            "JOIN o.store s " +
            "JOIN o.customer c " +
            "WHERE (:disputeOrderId IS NULL OR d.disputeOrderId = :disputeOrderId) " +
            "AND (:orderId IS NULL OR o.orderId = :orderId) " +
            "AND (:storeId IS NULL OR o.store.storeId = :storeId) " +
            "AND (:storeName IS NULL OR s.storeName LIKE %:storeName%) " +
            "AND (:memberNickname IS NULL OR c.nickname LIKE %:memberNickname%) " +
            "AND (:disputeStatus IS NULL OR d.disputeStatus = :disputeStatus) " +
            "AND (:dateStart IS NULL OR d.applyDatetime >= :dateStart) " +
            "AND (:dateEnd IS NULL OR d.applyDatetime <= :dateEnd)")
    Page<DisputeOrder> findByCriteria(
            @Param("disputeOrderId") Integer disputeOrderId,
            @Param("orderId") Integer orderId,
            @Param("storeId") Integer storeId,
            @Param("storeName") String storeName,
            @Param("memberNickname") String memberNickname,
            @Param("disputeStatus") Integer disputeStatus,
            @Param("dateStart") Timestamp dateStart,
            @Param("dateEnd") Timestamp dateEnd,
            Pageable pageable
    );

}