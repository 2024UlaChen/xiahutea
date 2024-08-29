package idv.tia201.g2.web.member.dao;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;

import java.util.List;

public interface MemberDao {
     List<Member> findAll();

     Member findByMemberId(int memberId);

//     Member findByMemberPhone(String phone);
//
//     Member findByMemberEmail(String email);

     int createMember(Member member);

     int deleteMemberAddress(int memberId);

     List<MemberAddress> findMemberAddressByMemberId(String memberId);

     Member findMemberForLogin(String username, String password);

     int updateMemberInfo(Member member);

     List<Member> findByMemberValidStatus(boolean status);


}
