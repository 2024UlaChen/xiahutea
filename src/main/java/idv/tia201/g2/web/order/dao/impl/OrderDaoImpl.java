package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.vo.Orders;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private Session session;

    @Override
    public int insert(Orders orders) {
        session.persist(orders);
        return 1;
    }

    @Override
    public int update(Orders orders) {
        session.merge(orders);
        return 1;
    }

    // TODO: 待確認是否需要
    @Override
    public Orders selectByOrderId(int orderId) {
        String hql = "FROM Orders WHERE orderId= :orderId";
        TypedQuery<Orders> query = session.createQuery(hql, Orders.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    @Override
    public List<Object[]> selectBycCustomerId(int customerId) {
        String sql = "SELECT o.*, d.* FROM orders o " +
                "LEFT JOIN dispute_order d ON o.order_id = d.order_id " +
                "LEFT JOIN store s ON o.store_id = s.store_id " +
                " WHERE o.customer_id = :customerId";
        NativeQuery nativeQuery  = session.createNativeQuery(sql)
                .setParameter("customerId", customerId);
        return nativeQuery.getResultList();

//        String hql = "FROM Orders o JOIN DisputeOrder d WITH o.orderID = d.orderId WHERE customerId = :customerId";
//        TypedQuery<Orders> query = session.createQuery(hql, Orders.class)
//                .setParameter("customerId", customerId);
//        return query.getResultList();
    }

    @Override
    public List<Orders> selectAll() {
        TypedQuery<Orders> query = session.createQuery("FROM Orders", Orders.class);
        return query.getResultList();
    }
}