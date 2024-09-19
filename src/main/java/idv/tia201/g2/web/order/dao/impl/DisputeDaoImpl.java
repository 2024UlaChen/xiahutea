package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class DisputeDaoImpl implements DisputeDao {

    @PersistenceContext
    private Session session;

    // FINISH
    // 後台 爭議列表
    @Override
    public List<DisputeOrder> selectAll() {
        String hql = "from DisputeOrder";
        TypedQuery<DisputeOrder> query = session.createQuery(hql, DisputeOrder.class);
        return query.getResultList();
    }

    // 後台 爭議明細
    @Override
    public DisputeOrder selectByDisputeId(Integer disputeOrderId) {
        String hql = "from DisputeOrder where disputeOrderId= :disputeOrderId";
        TypedQuery<DisputeOrder> query = session.createQuery(hql, DisputeOrder.class)
                .setParameter("disputeOrderId", disputeOrderId);
        return query.getSingleResult();
    }

    // todo 前台訂單
    @Override
    public List<DisputeOrder> selectBycCustomerId(Integer customerId) {
        String hql = "from DisputeOrder where customerId= :customerId";
        TypedQuery<DisputeOrder> query = session.createQuery(hql, DisputeOrder.class)
                .setParameter("customerId", customerId);
        return query.getResultList();
    }

    //--------------------------------------
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

    // 前台 爭議申請 新增
    @Override
    public int insert(DisputeOrder disputeOrder) {
        session.persist(disputeOrder);
        return 1;
    }

    // 後台 爭議明細 儲存
    @Override
    public int update(DisputeOrder disputeOrder) {
        session.merge(disputeOrder);
        return 1;
    }


}
