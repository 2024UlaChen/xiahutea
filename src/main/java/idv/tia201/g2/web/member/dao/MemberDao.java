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
    int updateMemberInfo(Member member);

    int updateMemberAddress(MemberAddress memberAddress);

    //delete
    int deleteMemberAddress(int memberId);

    //create
    int createMember(Member member);

    int createMemberAddress(MemberAddress memberAddress);

}
