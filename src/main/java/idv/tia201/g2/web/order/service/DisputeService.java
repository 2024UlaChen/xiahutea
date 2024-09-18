package idv.tia201.g2.web.order.service;

import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.Orders;

import java.util.List;

public interface DisputeService  {

    List<DisputeOrder> findAll();
    OrderDto findByDisputeOrderId(int disputeOrderId);
    OrderDto findByOrderId(int orderId);
    DisputeOrder add(DisputeOrder disputeOrder);
    DisputeOrder updateInfo(DisputeOrder disputeOrder);

}