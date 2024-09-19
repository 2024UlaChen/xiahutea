package idv.tia201.g2.web.member.dao;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;

import java.util.List;

public interface MemberDao {
    List<Member> findAllMember();

    Member findMemberById(int memberId);

    Member findMemberByPhone(String phone);

//     Member findByMemberEmail(String email);

    Member findMemberForLogin(String username, String password);

    List<Member> findMemberByValidStatus(Boolean status);

    List<Member> findMemberByNickname(String nickname);

    List<Member> findMemberByQueryParam(String nickname, Integer memberId, String phone, Boolean status);

    List<MemberAddress> findMemberAddressByMemberId(String memberId);

    //update
    boolean updateMemberInfo(Member member);

    Integer updateMemberInfo(Integer memberId, Boolean status, String memberRemark);

    boolean updateMemberAddress(MemberAddress memberAddress);

    //delete
    Integer deleteByMemberAddressId(Integer memberId);

    //create
    boolean createMember(Member member);

    boolean createMemberAddress(MemberAddress memberAddress);

    Integer updateMemberCarrierById(Integer memberId, String memberCarrier);

}
