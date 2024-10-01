package idv.tia201.g2.web.order.dao;

import java.util.List;
import idv.tia201.g2.web.order.vo.Orders;

public interface OrderDao {

    int insert(Orders orders);
    int update(Orders orders);
    void saveInvoiceNo(Integer orderId, String invoiceNo);

    List<Orders> selectAll();
    Orders selectByOrderId(int orderId);
    List<Orders> selectBycCustomerId(int customerId);

}