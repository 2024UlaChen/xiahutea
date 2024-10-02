package idv.tia201.g2.web.order.service;

import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DisputeService  {

    DisputeOrder add(DisputeOrder disputeOrder);
    DisputeOrder updateInfo(DisputeOrder disputeOrder);

    Page<DisputeOrder> findAll(Pageable pageable);
    OrderDto findByOrderId(int orderId);
    OrderDto findByDisputeOrderId(int disputeOrderId);
}