package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.vo.Orders;
import idv.tia201.g2.web.user.vo.Administrators;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    //todo

    @PersistenceContext
    private EntityManager em;

    @Override
    public int insert(Orders orders) {
        return -1;
    }

    @Override
    public int update(Orders orders) {
        return 0;
    }

    @Override
    public Orders selectByOrderId(int orderId) {
        String jpql = "from Orders where orderId= :orderId";
        TypedQuery<Orders> query = em.createQuery(jpql, Orders.class)
                .setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    @Override
    public Orders selectBycCustomerId(int customerId) {
        String jpql = "from Orders where customerId= :customerId";
        TypedQuery<Orders> query = em.createQuery(jpql, Orders.class)
                .setParameter("customerId", customerId);
        return query.getSingleResult();
    }

    @Override
    public List<Orders> selectAll() {
        String jpql = "from Orders";
        TypedQuery<Orders> query = em.createQuery(jpql, Orders.class);
        return query.getResultList();

    }
}
