package idv.tia201.g2.web.member.dao;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberDao {
    List<Member> findAllMember();

    Member findMemberById(Integer memberId);

    Member findMemberByPhone(String phone);


    Member findMemberForLogin(String username, String password);

    List<Member> findMemberByQueryParam(String nickname, Integer memberId, String phone, Boolean status, int pageNo);

    //update
    Member updateMember(Member member);

    Boolean updateMemberInfo(Member member);

    Integer updateMemberInfo(Integer memberId, Boolean aliveStatus, String memberRemark);

    Boolean updateMemberAddress(MemberAddress memberAddress);

    Integer updateMemberMoney(Integer memberId, Integer memberMoney);

    //delete
    Integer deleteByMemberAddressId(Integer memberId);

    //create
    void createMember(Member member);

    Integer updateMemberCarrierById(Integer memberId, String memberCarrier);

    Integer updateVerifyCodeById(Integer memberId, String verifyCode);


}
