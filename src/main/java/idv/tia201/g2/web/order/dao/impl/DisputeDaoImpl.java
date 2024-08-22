package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.Orders;

import java.util.List;

public class DisputeDaoImpl implements DisputeDao {
    @Override
    public int insert(DisputeOrder disputeOrder) {
        return 0;
    }

    @Override
    public int update(DisputeOrder disputeOrder) {
        return 0;
    }

    @Override
    public Orders selectByDisputeOrderId(int disputeOrderId) {
        return null;
    }

    @Override
    public List<DisputeOrder> selectAll() {
        return List.of();
    }
}
