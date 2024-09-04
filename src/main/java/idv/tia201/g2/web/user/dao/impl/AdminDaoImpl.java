package idv.tia201.g2.web.user.dao.impl;

import idv.tia201.g2.web.user.dao.AdminDao;
import idv.tia201.g2.web.user.vo.Administrator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDaoImpl implements AdminDao {

    @PersistenceContext
    private EntityManager em;

    public Administrator login(String username, String password) {
//        ORM 敘述
        String jpql = "from Administrator where adminUsername = :username and adminPassword = :password";
//        帶入 & 搜尋
        TypedQuery<Administrator> query = em.createQuery(jpql, Administrator.class)
                .setParameter("username", username)
                .setParameter("password", password);
//        getSingleResult() 可能因為查無資料所以拋出異常
//        uniqueResult() 不受 Spring Data JPA 支援
        List<Administrator> resultList = query.getResultList();
//        如果取得空陣列代表查無會員資料
        if (resultList.isEmpty()){
            return null;
        }else {
            return resultList.get(0);
        }
    }
}
