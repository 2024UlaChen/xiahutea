package idv.tia201.g2.web.member.dao;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;

import java.util.List;

public interface MemberAddrDao {

    List<MemberAddress> findMemberAddressByMemberId(String memberId);

    boolean updateMemberAddress(MemberAddress memberAddress);
    //delete
    int deleteByMemberAddressId(int memberId);

    boolean createMemberAddress(MemberAddress memberAddress);

}
