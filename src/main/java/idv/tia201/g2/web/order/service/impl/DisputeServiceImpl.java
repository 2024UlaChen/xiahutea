package idv.tia201.g2.web.order.service.impl;

import java.util.List;

import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.dao.OrderDetailDao;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.DisputeService;
import idv.tia201.g2.web.order.util.OrderMappingUtil;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DisputeServiceImpl implements DisputeService {

    @Autowired
    private DisputeDao disputeDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private OrderMappingUtil orderMappingUtil;

    //後台 爭議列表
    @Override
    public List<DisputeOrder> findAll() {
        return disputeDao.selectAll();
    }


    // todo
    // 後台 爭議明細
    @Override
    public OrderDto findByDisputeOrderId(int disputeOrderId) {
        DisputeOrder disputeOrder = disputeDao.selectByDisputeId(disputeOrderId);
        if(disputeOrder == null) {
            return null;
        }
        Orders order = orderDao.selectByOrderId(disputeOrder.getOrderId());
        Member member = order.getCustomer();
        Store store = order.getStore();
        List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(disputeOrder.getOrderId());

        return orderMappingUtil.createOrderDto(order, disputeOrder, orderDetails, member, store);
    }




    @Override
    public DisputeOrder add(DisputeOrder disputeOrder) {
//        return (disputeDao.insert(disputeOrder));
        return null;
    }

    @Override
    public OrderDto update(OrderDto orderDto) {


        return null;
    }




}
