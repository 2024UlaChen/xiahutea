package idv.tia201.g2.web.user.dao.impl;

import idv.tia201.g2.web.user.dao.AdminDao;
import idv.tia201.g2.web.user.vo.Administrators;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDaoImpl implements AdminDao {

    @PersistenceContext
    private EntityManager em;

    public Administrators selectForLogin(String username, String password) {

        String jpql = "from Administrators where adminUsername = :username and adminPassword = :password";
        TypedQuery<Administrators> query = em.createQuery(jpql, Administrators.class)
                .setParameter("username", username)
                .setParameter("password", password);
        Administrators administrator = query.getSingleResult();
        return administrator;

    }
}
