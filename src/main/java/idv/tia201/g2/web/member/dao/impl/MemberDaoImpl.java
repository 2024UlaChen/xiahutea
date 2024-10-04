package idv.tia201.g2.web.member.dao.impl;

import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class MemberDaoImpl implements MemberDao {
    @PersistenceContext
    private Session session;

    @Override
    public List<Member> findAllMember() {
        return session.createQuery("FROM Member", Member.class).getResultList();
    }

    @Override
    public Member findMemberById(Integer memberId) {
        return session.get(Member.class, memberId);
    }

    @Override
    public Member findMemberByPhone(String phone) {
        final String sql = "select * from CUSTOMER where CUSTOMER_PHONE = :phone ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("phone", phone)
                .uniqueResult();
    }


    @Override
    public Member findMemberForLogin(String phone, String password) {
        final String sql = "select * from CUSTOMER where CUSTOMER_PHONE = :phone and CUSTOMER_PASSWORD = :password";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("phone", phone)
                .setParameter("password", password)
                .uniqueResult();
    }


    @Override
    public List<Member> findMemberByQueryParam(String nickname, Integer customerId, String phone, Boolean status, int pageNo) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> root = cq.from(Member.class);
        Predicate statusCondition, idCondition, phoneCondition, nameCondition;
        if (status == null) {
            statusCondition = cb.isNotNull(root.get("validStatus"));
        } else {
            statusCondition = cb.equal(root.get("validStatus"), status);
        }
        if (customerId == null) {
            idCondition = cb.isNotNull(root.get("customerId"));
        } else {
            idCondition = cb.equal(root.get("customerId"), customerId);
        }
        if (!StringUtils.hasText(phone)) {
            phoneCondition = cb.isNotNull(root.get("customerPhone"));
        } else {
            phoneCondition = cb.like(root.get("customerPhone"), "%" + phone + "%");
        }
        if (!StringUtils.hasText(nickname)) {
            nameCondition = cb.isNotNull(root.get("nickname"));
        } else {
            nameCondition = cb.like(root.get("nickname"), "%" + nickname + "%");
        }
        Predicate finalCondition = cb.and(nameCondition, statusCondition, phoneCondition, idCondition);
        cq.where(finalCondition);
        Query<Member> query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Boolean updateMemberInfo(Member member) {
        session.merge(member);
        return true;
    }

    @Override
    public Member updateMember(Member member) {
        return session.merge(member);
    }

    @Override
    public Integer updateMemberInfo(Integer memberId, Boolean aliveStatus, String memberRemark) {
        final String sql = "update CUSTOMER set alive_status = :aliveStatus ," +
                " customer_remark = :memberRemark  where  customer_id = :memberId ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("aliveStatus", aliveStatus)
                .setParameter("memberRemark", memberRemark)
                .setParameter("memberId", memberId)
                .executeUpdate();
    }

    @Override
    public Integer updateMemberCarrierById(Integer memberId, String memberCarrier) {
        final String sql = "update CUSTOMER set customer_carrier = :memberCarrier   where  customer_id = :memberId ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("memberCarrier", memberCarrier)
                .setParameter("memberId", memberId)
                .executeUpdate();
    }

    @Override
    public Integer updateMemberMoney(Integer memberId, Integer memberMoney) {
        final String sql = "update CUSTOMER set customer_money =  customer_money + :memberMoney   where  customer_id = :memberId ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("memberMoney", memberMoney)
                .setParameter("memberId", memberId)
                .executeUpdate();
    }

    @Override
    public Boolean updateMemberAddress(MemberAddress memberAddress) {
        session.merge(memberAddress);
        return true;
    }

    @Override
    public Integer deleteByMemberAddressId(Integer memberAddressId) {
        MemberAddress memberAddress = session.load(MemberAddress.class, memberAddressId);
        session.remove(memberAddress);
        return 1;
    }

    @Override
    public void createMember(Member member) {
        session.persist(member);
    }

    @Override
    public Integer updateVerifyCodeById(Integer memberId, String verifyCode) {
        final String sql = "update CUSTOMER set verify_code = :verifyCode  , alive_status= :aliveStatus   where  customer_id = :memberId ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("verifyCode", verifyCode)
                .setParameter("aliveStatus", false)
                .setParameter("memberId", memberId)
                .executeUpdate();
    }
}
