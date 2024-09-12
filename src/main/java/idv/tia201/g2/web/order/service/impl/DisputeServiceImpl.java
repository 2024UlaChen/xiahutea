package idv.tia201.g2.web.order.service.impl;

import java.util.List;
import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.service.DisputeService;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DisputeServiceImpl implements DisputeService {

    @Autowired
    private DisputeDao disputeDao;

    // todo

    @Override
    public DisputeOrder add(DisputeOrder disputeOrder) {


        return null;
    }

    @Override
    public DisputeOrder update(DisputeOrder disputeOrder) {


        return null;
    }

    @Override
    public List<DisputeOrder> findAll() {
        return disputeDao.selectAll();
    }

    @Override
    public DisputeOrder findByDisputeId(int disputeOrderId) {
        return disputeDao.selectByDisputeId(disputeOrderId);
    }
}
