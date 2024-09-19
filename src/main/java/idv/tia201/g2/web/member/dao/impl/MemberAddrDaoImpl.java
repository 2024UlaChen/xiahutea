package idv.tia201.g2.web.member.dao.impl;

import idv.tia201.g2.web.member.dao.MemberAddrDao;
import idv.tia201.g2.web.member.vo.MemberAddress;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberAddrDaoImpl implements MemberAddrDao {
    @PersistenceContext
    private Session session;



    @Override
    public List<MemberAddress> findAddressByMemberId(Integer memberId) {
        final String sql = "select * from CUSTOMER_ADDRESS where customer_id = :memberId ";
        return session
                .createNativeQuery(sql, MemberAddress.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public boolean updateMemberAddress(MemberAddress memberAddress) {
        return false;
    }

    @Override
    public int deleteByMemberAddressId(int memberId) {
        return 0;
    }

    @Override
    public boolean createMemberAddress(MemberAddress memberAddress) {
        return false;
    }
}
