package idv.tia201.g2.web.member.service.impl;

import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

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
    public boolean isExistMember(Member member) {
        return false;
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
    public boolean deleteByMemberAddressId(Integer customerAddressId) {
        try {
            int resultCount = memberDao.deleteByMemberAddressId(customerAddressId);
            return resultCount > 0;
        } catch (Exception e) {
//            logger.error();
            return false;
        }
    }


    //todo
    @Override
    public Member getMemberInfo(Integer memberId) {
        return null;
    }

}
