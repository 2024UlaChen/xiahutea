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
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public Member findByMemberId(int memberId) {
        return session.get(Member.class, memberId);
    }

    @Override
    public Member findByMemberPhone(String phone) {
        //todo revise return data
        return session.get(Member.class, phone);
    }

    @Override
    public Member findMemberForLogin(String username, String password) {
        return null;
    }

    @Override
    public List<Member> findByMemberValidStatus(boolean status) {
        return List.of();
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
    public boolean updateMemberAddress(MemberAddress memberAddress) {
        session.merge(memberAddress);
        return true;
    }

    @Override
    public int deleteByMemberAddressId(int memberAddressId) {
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
