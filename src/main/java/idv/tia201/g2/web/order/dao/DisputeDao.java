package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.DisputeOrder;

public interface DisputeDao {

    int insert(DisputeOrder disputeOrder);
    int update(DisputeOrder disputeOrder);

    DisputeOrder selectByOrderId(Integer orderId);
    DisputeOrder selectByDisputeId(Integer disputeOrderId);

}