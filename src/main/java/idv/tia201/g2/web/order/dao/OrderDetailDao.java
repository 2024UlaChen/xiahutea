package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.OrderDetail;
import java.util.List;

public interface OrderDetailDao {

    List<OrderDetail> selectByOrderId(int orderId);
    List<OrderDetail> selectCustomerId(int customerId);
    int insert(OrderDetail orderDetail);

}
