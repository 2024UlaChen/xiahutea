package idv.tia201.g2.web.member.dao;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;

import java.util.List;

public interface MemberDao {
    List<Member> findAll();

    Member findByMemberId(int memberId);

//     Member findByMemberPhone(String phone);

//     Member findByMemberEmail(String email);

    Member findMemberForLogin(String username, String password);

    List<Member> findByMemberValidStatus(boolean status);

    List<MemberAddress> findMemberAddressByMemberId(String memberId);

    //update
    boolean updateMemberInfo(Member member);

    boolean updateMemberAddress(MemberAddress memberAddress);

    //delete
    int deleteByMemberAddressId(int memberId);

    //create
    boolean createMember(Member member);

    boolean createMemberAddress(MemberAddress memberAddress);

}
