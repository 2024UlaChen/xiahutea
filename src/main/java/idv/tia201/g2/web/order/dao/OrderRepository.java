package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    // 後台 訂單列表 顯示
    @Query("FROM Orders")
    Page<Orders> findAll(Pageable pageable);

}
