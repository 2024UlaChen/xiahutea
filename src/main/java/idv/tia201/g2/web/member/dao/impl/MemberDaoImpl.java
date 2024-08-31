package idv.tia201.g2.web.member.dao.impl;

import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;

import java.util.List;

public class MemberDaoImpl implements MemberDao {


    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public Member findByMemberId(int memberId) {
        return null;
    }

    @Override
    public Member findMemberForLogin(String username, String password) {
        return null;
    }

    @Override
    public List<Member> findByMemberValidStatus(boolean status) {
        return List.of();
    }

    @Override
    public List<MemberAddress> findMemberAddressByMemberId(String memberId) {
        return List.of();
    }

    @Override
    public int updateMemberInfo(Member member) {
        return 0;
    }

    @Override
    public int updateMemberAddress(MemberAddress memberAddress) {
        return 0;
    }

    @Override
    public int deleteMemberAddress(int memberId) {
        return 0;
    }

    @Override
    public int createMember(Member member) {
        return 0;
    }

    @Override
    public int createMemberAddress(MemberAddress memberAddress) {
        return 0;
    }
}
