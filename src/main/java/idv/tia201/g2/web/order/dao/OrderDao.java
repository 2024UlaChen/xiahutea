package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.Orders;

import java.util.List;

public interface OrderDao {
    boolean insert(Orders orders);
    boolean update(Orders orders);

    Orders selectByOrderId(int orderId);
    List<Orders> selectBycCustomerId(int customerId);
    List<Orders> selectAll();
}
