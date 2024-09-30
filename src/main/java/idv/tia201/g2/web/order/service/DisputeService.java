package idv.tia201.g2.web.order.service;

import java.util.List;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.DisputeOrder;

public interface DisputeService  {

    DisputeOrder add(DisputeOrder disputeOrder);
    DisputeOrder updateInfo(DisputeOrder disputeOrder);

    List<DisputeOrder> findAll();
    OrderDto findByOrderId(int orderId);
    OrderDto findByDisputeOrderId(int disputeOrderId);
}