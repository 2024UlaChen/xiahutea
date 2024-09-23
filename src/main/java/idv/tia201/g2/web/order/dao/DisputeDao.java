package idv.tia201.g2.web.order.dao;

import java.util.List;
import idv.tia201.g2.web.order.vo.DisputeOrder;

public interface DisputeDao {

    int insert(DisputeOrder disputeOrder);
    int update(DisputeOrder disputeOrder);

    DisputeOrder selectByOrderId(Integer orderId);
    DisputeOrder selectByDisputeId(Integer disputeOrderId);
    List<DisputeOrder> selectAll();

}