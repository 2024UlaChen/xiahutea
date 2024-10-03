package idv.tia201.g2.web.order.service;

import java.sql.Timestamp;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DisputeService  {

    DisputeOrder add(DisputeOrder disputeOrder);
    DisputeOrder updateInfo(DisputeOrder disputeOrder);

    OrderDto findByOrderId(Integer orderId);
    OrderDto findByDisputeOrderId(Integer disputeOrderId);

    Page<DisputeOrder> findByCriteria(
            Integer disputeOrderId, Integer orderId, Integer storeId, String storeName, String memberNickname,
            Integer disputeStatus, Timestamp dateStart, Timestamp dateEnd, Pageable pageable
    );

}