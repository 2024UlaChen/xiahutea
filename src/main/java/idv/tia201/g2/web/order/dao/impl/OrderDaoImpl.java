package idv.tia201.g2.web.order.dao.impl;

import java.util.List;
import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.vo.Orders;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private Session session;

    // ------- FINISH ---------------------------
    // 前台 訂單新增
    @Override
    public int insert(Orders orders) {
        session.persist(orders);
        return 1;
    }

    // 前台 訂單列表 顯示
    @Override
    public List<Orders> selectBycCustomerId(int customerId) {
        String hql = "FROM Orders WHERE customerId = :customerId";
        TypedQuery<Orders> query = session.createQuery(hql, Orders.class)
                .setParameter("customerId", customerId);
        return query.getResultList();
    }

    // 後台 訂單明細 新增
    @Override
    public int update(Orders orders) {
        Orders result = session.merge(orders);
        return result != null ? 1 : 0 ;
    }

    // 後端 訂單新增 > 發票新增
    @Override
    public void saveInvoiceNo(Integer orderId, String invoiceNo) {
        String hql = "UPDATE Orders o SET o.invoiceNo = :invoiceNo WHERE o.orderId = :orderId";
        session
                .createQuery(hql)
                .setParameter("orderId", orderId)
                .setParameter("invoiceNo", invoiceNo)
                .executeUpdate();
    }

    // 後台 訂單明細 顯示
    @Override
    public Orders selectByOrderId(int orderId) {
        String hql = "FROM Orders WHERE orderId= :orderId";
        TypedQuery<Orders> query = session.createQuery(hql, Orders.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    // 後台 訂單列表 顯示
//    @Override
//    public List<Orders> selectAll() {
//        TypedQuery<Orders> query = session.createQuery("FROM Orders", Orders.class);
//        return query.getResultList();
//    }
}