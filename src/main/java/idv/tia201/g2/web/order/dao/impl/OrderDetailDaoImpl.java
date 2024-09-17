package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.OrderDetailDao;
import idv.tia201.g2.web.order.vo.OrderDetail;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDetailDaoImpl implements OrderDetailDao {

    @PersistenceContext
    private Session session;

    @Override
    public int insert(OrderDetail orderDetail) {
        session.merge(orderDetail);
        return 1;
    }

    // 後台訂單明細
    @Override
    public List<OrderDetail> selectByOrderId(int orderId) {
        String hql = "FROM OrderDetail WHERE orderId= :orderId";
        TypedQuery<OrderDetail> query = session.createQuery(hql, OrderDetail.class)
                .setParameter("orderId", orderId);
        return query.getResultList();
    }
}
