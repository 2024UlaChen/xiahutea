package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DisputeRepository extends JpaRepository<DisputeOrder,Integer > {

    // 後台 爭議列表 顯示
    @Query("FROM DisputeOrder")
    Page<DisputeOrder> findAll(Pageable pageable);

}