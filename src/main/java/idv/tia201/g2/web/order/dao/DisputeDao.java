package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DisputeDao {

    List<DisputeOrder> selectAll();
    DisputeOrder selectByDisputeId(Integer disputeOrderId);
    List<DisputeOrder> selectBycCustomerId(Integer customerId);
    DisputeOrder selectByOrderId(Integer orderId);
    int insert(DisputeOrder disputeOrder);
    int update(DisputeOrder disputeOrder);

}