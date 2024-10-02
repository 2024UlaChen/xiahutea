package idv.tia201.g2.web.order.dao;

import idv.tia201.g2.web.order.vo.Orders;

public interface OrderDao {

    int insert(Orders orders);
    int update(Orders orders);
    void saveInvoiceNo(Integer orderId, String invoiceNo);
    Orders selectByOrderId(int orderId);

}