package idv.tia201.g2.web.order.service;

import idv.tia201.g2.web.order.vo.Orders;
import java.util.List;

public interface OrderService {

    Orders addOrder(Orders order);
    Orders addStar(Orders order);

    Orders update(Orders order);
    List<Orders> findAll();

}
