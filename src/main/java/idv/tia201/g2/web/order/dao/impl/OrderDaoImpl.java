package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.vo.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDaoImpl implements OrderDao {

    //todo

    @Override
    public int insert(Orders orders) {
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
//        try {
//            Transaction transaction = session.beginTransaction();
//            session.persist(orders);
//            transaction.commit();
//            return orders.getId();
//        } catch (HibernateException e) {
//            session.getTransaction().rollback();
//            e.printStackTrace();
//        }
       return -1;
    }

    @Override
    public int update(Orders orders) {
        return 0;
    }

    @Override
    public Orders selectByOrderId(int orderId) {
        return null;
    }

    @Override
    public Orders selectBycCustomerId(int customerId) {
        return null;
    }

    @Override
    public List<Orders> selectAll() {
        return List.of();
    }
}
