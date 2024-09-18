package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.vo.Orders;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private Session session;

    // FINISH 0916
    @Override
    public List<Orders> selectAll() {
        TypedQuery<Orders> query = session.createQuery("FROM Orders", Orders.class);
        return query.getResultList();
    }

    @Override
    public Orders selectByOrderId(Integer orderId) {
        String hql = "FROM Orders WHERE orderId= :orderId";
        TypedQuery<Orders> query = session.createQuery(hql, Orders.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    @Override
    public int update(Orders orders) {
        Orders result = session.merge(orders);
        return result != null ? 1 : 0 ;
    }

    //-------------------------------
    // TODO: 待確認是否需要
    @Override
    public List<Orders> selectBycCustomerId(Integer customerId) {
        String hql = "FROM Orders WHERE customerId = :customerId";
        TypedQuery<Orders> query = session.createQuery(hql, Orders.class)
                .setParameter("customerId", customerId);
        return query.getResultList();
    }

    @Override
    public int insert(Orders orders) {
        session.persist(orders);
        return 1;
    }






}