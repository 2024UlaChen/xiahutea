package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.vo.Orders;

import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Override
    public int insert(Orders orders) {
        return 0;
    }

    @Override
    public int update(Orders orders) {
        return 0;
    }

    @Override
    public Orders selectByOrderId(int orderId) {
        return null;
    }

    @Override
    public Orders selectBycCustomerId(int customerId) {
        return null;
    }

    @Override
    public List<Orders> selectAll() {
        return List.of();
    }
}
