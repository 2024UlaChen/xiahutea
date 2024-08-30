package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.Orders;
import java.util.List;

public interface DisputeDao {
    int insert(DisputeOrder disputeOrder);
    int update(DisputeOrder disputeOrder);

    Orders selectByDisputeId(int disputeId);
    List<DisputeOrder> selectAll();

}
