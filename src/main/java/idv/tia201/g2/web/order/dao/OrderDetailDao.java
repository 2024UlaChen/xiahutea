package idv.tia201.g2.web.order.dao;

import java.util.List;
import idv.tia201.g2.web.order.vo.OrderDetail;

public interface OrderDetailDao {

    int insert(OrderDetail orderDetail);
    List<OrderDetail> selectByOrderId(int orderId);

}