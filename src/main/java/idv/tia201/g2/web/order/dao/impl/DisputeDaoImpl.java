package idv.tia201.g2.web.order.dao.impl;

import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DisputeDaoImpl implements DisputeDao {

    @PersistenceContext
    private Session session;

    @Override
    public int insert(DisputeOrder disputeOrder) {
        session.persist(disputeOrder);
        return 1;
    }

    @Override
    public int update(DisputeOrder disputeOrder) {
        session.merge(disputeOrder);
        return 1;
    }

    @Override
    public DisputeOrder selectByDisputeId(int disputeOrderId) {
        String jpql = "from DisputeOrder where disputeOrderId= :disputeOrderId";
        TypedQuery<DisputeOrder> query = session.createQuery(jpql, DisputeOrder.class)
                .setParameter("disputeOrderId", disputeOrderId);
        return query.getSingleResult();
    }

    @Override
    public List<DisputeOrder> selectAll() {
        String jpql = "from DisputeOrder";
        TypedQuery<DisputeOrder> query = session.createQuery(jpql, DisputeOrder.class);
        return query.getResultList();
    }
}
