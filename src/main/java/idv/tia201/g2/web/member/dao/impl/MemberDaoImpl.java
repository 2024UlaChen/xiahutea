package idv.tia201.g2.web.member.dao.impl;

import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;

import java.util.List;

public class MemberDaoImpl implements MemberDao {

    @Override
    public List<Member> selecetAll() {
        return null;
    }

    @Override
    public Member selectByMemberId(int memberId) {
        return null;
    }

    @Override
    public int createMember(Member member) {
        return 0;
    }

    @Override
    public int deleteMemberAddress(int memberId) {
        return 0;
    }

    @Override
    public List<MemberAddress> selectMemberAddressByMemberId(String memberId) {
        return null;
    }

    @Override
    public Member selectMemberForLogin(String username, String password) {
        return null;
    }

    @Override
    public int updateMemberInfo(Member member) {
        return 0;
    }
}
