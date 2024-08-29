package idv.tia201.g2.web.order.service.impl;

import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;


    // todo
    // 發票api
    // 評分

    public Orders addStar(Orders order){

        return null;
    }

    @Override
    public Orders addOrder(Orders order) {
        return null;
    }

    @Override
    public Orders update(Orders order) {
        return null;
    }

    @Override
    public List<Orders> findAll() {
        return orderDao.selectAll();
    }
}
