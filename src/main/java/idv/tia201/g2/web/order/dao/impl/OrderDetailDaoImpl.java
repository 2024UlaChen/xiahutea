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

    // ------- FINISH ---------------------------
    // 後端 訂單新增 > 明細新增
    @Override
    public int insert(OrderDetail orderDetail) {
        session.merge(orderDetail);
        return 1;
    }

    // 前後台 訂單&爭議明細 顯示
    @Override
    public List<OrderDetail> selectByOrderId(int orderId) {
        String hql = "FROM OrderDetail WHERE orderId= :orderId";
        TypedQuery<OrderDetail> query = session.createQuery(hql, OrderDetail.class)
                .setParameter("orderId", orderId);
        return query.getResultList();
    }
}
