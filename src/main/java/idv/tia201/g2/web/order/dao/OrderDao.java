package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.Orders;

import java.util.List;

public interface OrderDao {

    List<Orders> selectAll();
    Orders selectByOrderId(Integer orderId);

    List<Orders> selectBycCustomerId(Integer customerId);
    int insert(Orders orders);
    int update(Orders orders);

}