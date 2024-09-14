package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.OrderDetail;

import java.util.List;

public interface OrderDetailDao {

    int insert(OrderDetail orderDetail);
    List<OrderDetail> selectByOrderId(int orderId);

}
