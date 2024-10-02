package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class DisputeDaoImpl implements DisputeDao {

    @PersistenceContext
    private Session session;

    // ------- FINISH ---------------------------
    // 前台 爭議申請 新增
    @Override
    public int insert(DisputeOrder disputeOrder) {
        session.persist(disputeOrder);
        return 1;
    }

    // 前台 爭議申請 顯示
    @Override
    public DisputeOrder selectByOrderId(Integer orderId) {
        String hql = "FROM DisputeOrder WHERE orderId= :orderId";
        TypedQuery<DisputeOrder> query = session.createQuery(hql, DisputeOrder.class)
                .setParameter("orderId", orderId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // 後台 爭議明細 儲存
    @Override
    public int update(DisputeOrder disputeOrder) {
        session.merge(disputeOrder);
        return 1;
    }

    // 後台 爭議明細 顯示
    @Override
    public DisputeOrder selectByDisputeId(Integer disputeOrderId) {
        String hql = "from DisputeOrder where disputeOrderId= :disputeOrderId";
        TypedQuery<DisputeOrder> query = session.createQuery(hql, DisputeOrder.class)
                .setParameter("disputeOrderId", disputeOrderId);
        return query.getSingleResult();
    }

}