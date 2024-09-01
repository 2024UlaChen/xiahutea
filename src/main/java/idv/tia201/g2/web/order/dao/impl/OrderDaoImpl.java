package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.vo.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private Session session;
  //  private EntityManager em;


    @Override
    public boolean insert(Orders orders) {
        session.persist(orders);
        return true;
    }

    @Override
    public boolean update(Orders orders) {
        session.merge(orders);
        return true;
    }

    @Override
    public Orders selectByOrderId(int orderId) {
        String jpql = "from Orders where orderId= :orderId";
        TypedQuery<Orders> query = session.createQuery(jpql, Orders.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    @Override
    public List<Orders> selectBycCustomerId(int customerId) {
        String jpql = "from Orders where customerId= :customerId";
        TypedQuery<Orders> query = session.createQuery(jpql, Orders.class)
                .setParameter("customerId", customerId);
        return query.getResultList();
    }

    @Override
    public List<Orders> selectAll() {
        String jpql = "from Orders";
        TypedQuery<Orders> query = session.createQuery(jpql, Orders.class);
        return query.getResultList();
    }
}