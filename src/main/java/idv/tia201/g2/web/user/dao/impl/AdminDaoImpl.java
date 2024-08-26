package idv.tia201.g2.web.user.dao.impl;

import idv.tia201.g2.web.user.dao.AdminDao;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.jpa.provider.HibernateUtils;

public class AdminDaoImpl implements AdminDao {

//    Session session = getSession();
//    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//    CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
//
//    // from Member
//    Root<Member> root = criteriaQuery.from(Member.class);
//
//    //where USERNAME
//        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
//
//    Query<Member> query = session.createQuery(criteriaQuery);
//        return query.uniqueResult();
}
