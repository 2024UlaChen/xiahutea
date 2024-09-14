package idv.tia201.g2.web.order.service.impl;

import java.util.List;
import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.dto.OrderDto;
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
//        return (disputeDao.insert(disputeOrder));
        return null;
    }

    @Override
    public OrderDto update(OrderDto orderDto) {


        return null;
    }

    @Override
    public List<DisputeOrder> findAll() {
        return disputeDao.selectAll();
    }

    @Override
    public OrderDto findByDisputeId(int disputeId) {
//        return disputeDao.selectByDisputeId(disputeOrderId);
        return null;
    }
}
