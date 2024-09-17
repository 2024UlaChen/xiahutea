package idv.tia201.g2.web.member.dao.impl;

import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

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
    public Member findMemberById(int memberId) {
        return session.get(Member.class, memberId);
    }

    @Override
    public Member findMemberByPhone(String phone) {
        final String sql = "select * from CUSTOMER where CUSTOMER_PHONE = :phone ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("phone", phone)
                .getResultList().get(0);
    }

    @Override
    public List<Member> findMemberByNickname(String nickname) {
        final String sql = "select * from CUSTOMER where nickname = :nickname ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    @Override
    public Member findMemberForLogin(String phone, String password) {
        //todo follow - need to revise
        final String sql = "select * from CUSTOMER where CUSTOMER_PHONE = :phone and CUSTOMER_PASSWORD = :password";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("phone", phone)
                .setParameter("password", password)
                .getResultList().get(0);
    }

    @Override
    public List<Member> findMemberByValidStatus(Boolean status) {
        final String sql = "select * from CUSTOMER where valid_status = :status ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public List<Member> findMemberByQueryParam(String nickname, Integer memberId, String phone, Boolean status) {
//        TODO REVISE
        final String sql = "select * from CUSTOMER where valid_status = :status and " +
                " nickname = :nickname and  CUSTOMER_PHONE = :phone and  customer_id = :memberId ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("nickname", nickname)
                .setParameter("phone", phone)
                .setParameter("memberId", memberId)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public List<MemberAddress> findMemberAddressByMemberId(String memberId) {
        return List.of();
    }

    @Override
    public boolean updateMemberInfo(Member member) {
        session.merge(member);
        return true;
    }

    @Override
    public Integer updateMemberInfo(Integer memberId, Boolean status, String memberRemark) {
        final String sql = "update CUSTOMER set valid_status = :status , " +
                " customer_remark = :memberRemark  where  customer_id = :memberId ";
        return session
                .createNativeQuery(sql, Member.class)
                .setParameter("status", status)
                .setParameter("memberRemark", memberRemark)
                .setParameter("memberId", memberId)
                .executeUpdate();

    }

    @Override
    public boolean updateMemberAddress(MemberAddress memberAddress) {
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
    public boolean createMember(Member member) {
        session.persist(member);
        return true;
    }

    @Override
    public boolean createMemberAddress(MemberAddress memberAddress) {
        session.persist(memberAddress);
        return true;
    }

}
