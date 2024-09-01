package idv.tia201.g2.web.member.service.impl;

import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Override
    public Member register(Member member) {
        return null;
    }

    @Override
    public Member login(Member member) {
        return null;
    }

    @Override
    public Member editMember(Member member) {
        return null;
    }

    @Override
    public MemberAddress editMemberAddress(MemberAddress memberAddress) {
        return null;
    }

    @Override
    public List<Member> findAllMember() {
        return List.of();
    }

    @Override
    public List<MemberAddress> findAllMemberAdress() {
        return List.of();
    }

    @Override
    public boolean saveMember(Member member) {
        return false;
    }

    @Override
    public boolean saveMemberAddress(MemberAddress memberAddress) {
        return false;
    }

    @Override
    public boolean deleteMemberAddress(Integer customerAddressId) {
        return false;
    }
}
