package idv.tia201.g2.web.member.dao;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;

import java.util.List;

public interface MemberDao {
     List<Member> selecetAll();

     Member selectByMemberId(int memberId);

//     Member selectByMemberPhone(String phone);
//
//     Member selectByMemberEmail(String email);

     int createMember(Member member);

     int deleteMemberAddress(int memberId);

     List<MemberAddress> selectMemberAddressByMemberId(String memberId);

     Member selectMemberForLogin(String username, String password);

     int updateMemberInfo(Member member);


}
