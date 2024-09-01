package idv.tia201.g2.web.member.service;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;

import java.util.List;

public interface MemberService {
    Member register(Member member);

    Member login(Member member);

    Member editMember(Member member);

    MemberAddress editMemberAddress(MemberAddress memberAddress);

    List<Member> findAllMember();

    List<MemberAddress> findAllMemberAdress();

    boolean saveMember(Member member);

    boolean saveMemberAddress(MemberAddress memberAddress);

    boolean deleteMemberAddress(Integer customerAddressId);

}
