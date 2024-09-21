package idv.tia201.g2.web.member.service.impl;

import idv.tia201.g2.web.member.dao.MemberAddrDao;
import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;
    @Autowired
    MemberAddrDao memberAddrDao;

    @Override
    public Member register(Member member) {
        if (StringUtils.isEmpty(member.getCustomerPhone())) {
            member.setMessage("手機未輸入");
            member.setSuccessful(false);
            return member;
        }
        if (StringUtils.isEmpty(member.getCustomerPassword())) {
            member.setMessage("密碼未輸入");
            member.setSuccessful(false);
            return member;
        }
        return memberDao.findMemberById(member.getCustomerId());
    }

    @Override
    public Member login(Member member) {
        //todo follow - need to revise
        final String phone = member.getCustomerPhone();
        final String password = member.getCustomerPassword();

        if (phone == null) {
            member.setMessage("電話未輸入");
            member.setSuccessful(false);
            return member;
        }

        if (password == null) {
            member.setMessage("密碼未輸入");
            member.setSuccessful(false);
            return member;
        }

        member = memberDao.findMemberForLogin(phone, password);
        if (member == null) {
            member = new Member();
            member.setMessage("使用者電話或密碼錯誤");
            member.setSuccessful(false);
            return member;
        }
        member.setMessage("登入成功");
        member.setSuccessful(true);
        return member;
    }

    @Override
    public Member editMember(Member member) {
        return null;
    }

    @Override
    public Integer updateMemberByValidSatusAndCustomerRemark(Member member) {
        if (member.getCustomerRemark() == null) {
            member.setMessage("customer remark data is null");
            member.setSuccessful(false);
            return 0;
        }
        return memberDao.updateMemberInfo(member.getCustomerId(), member.getValidStatus(), member.getCustomerRemark());
    }

    @Override
    public MemberAddress editMemberAddress(MemberAddress memberAddress) {
        return null;
    }

    @Override
    public List<Member> findAllMember() {
        return memberDao.findAllMember();
    }


    @Override
    public Boolean isExistMember(Member member) {
        if (ObjectUtils.isEmpty(memberDao.findMemberByPhone(member.getCustomerPhone()))) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<MemberAddress> findAddressByMemberId(Integer memberId) {
        return memberAddrDao.findAddressByMemberId(memberId);
    }

    @Override
    public Boolean saveMember(Member member) {
        return false;
    }

    @Override
    public Boolean saveMemberAddress(MemberAddress memberAddress) {
        return false;
    }

    @Override
    public Boolean deleteByMemberAddressId(Integer customerAddressId) {
        try {
            int resultCount = memberDao.deleteByMemberAddressId(customerAddressId);
            return resultCount > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Member findMemberById(Integer memberId) {
        return memberDao.findMemberById(memberId);
    }

    @Override
    public List<Member> findMemberByValidStatus(Boolean memberValidStatus) {
        return memberDao.findMemberByValidStatus(memberValidStatus);
    }

    @Override
    public List<Member> findQueryMember(Member member) {
//        TODO REVISE
        if (!ObjectUtils.isEmpty(member.getValidStatus())) {
            return findMemberByValidStatus(member.getValidStatus());
        } else if (!StringUtils.isEmpty(member.getNickname())) {
            return memberDao.findMemberByNickname(member.getNickname());
        } else {
            List<Member> memberResultList = new ArrayList<>();
            if (!StringUtils.isEmpty(member.getCustomerPhone())) {
                memberResultList.add(memberDao.findMemberByPhone(member.getCustomerPhone()));
            }

            if (!StringUtils.isEmpty(member.getCustomerId())) {
                memberResultList.add(memberDao.findMemberById(member.getCustomerId()));
            }
            return memberResultList;
        }
//        String memberId = StringUtils.isEmpty(member.getCustomerId())?null:member.getCustomerId();
//        return memberDao.findMemberByQueryParam()
    }
}
