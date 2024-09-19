package idv.tia201.g2.web.order.dao.impl;

import java.util.List;
import idv.tia201.g2.web.order.dao.OrderDetailDao;
import idv.tia201.g2.web.order.vo.OrderDetail;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailDaoImpl implements OrderDetailDao {

    @PersistenceContext
    private Session session;

    // FINISH
    // 後台訂單明細
    @Override
    public List<OrderDetail> selectByOrderId(int orderId) {
        String hql = "FROM OrderDetail WHERE orderId= :orderId";
        TypedQuery<OrderDetail> query = session.createQuery(hql, OrderDetail.class)
                .setParameter("orderId", orderId);
        return query.getResultList();
    }

    // todo
    @Override
    public List<OrderDetail> selectCustomerId(int customerId) {
        String hql = "FROM OrderDetail WHERE customerId= :customerId";
        TypedQuery<OrderDetail> query = session.createQuery(hql, OrderDetail.class)
                .setParameter("customerId", customerId);
        return query.getResultList();
    }

    //---------------------------------------------
    // TODO
    @Override
    public int insert(OrderDetail orderDetail) {
        session.merge(orderDetail);
        return 1;
    }
}
