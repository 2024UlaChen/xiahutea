package idv.tia201.g2.web.member.service.impl;

import idv.tia201.g2.core.util.EncrypSHA;
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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    MemberAddrDao memberAddrDao;

    @Autowired
    private SendSmsService sendSmsService;

    public EncrypSHA encrypSHA;

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Member register(Member member) {
        String phone = member.getCustomerPhone();
        String pwd = member.getCustomerPassword();
        String memberName = member.getNickname();
        //todo need to revise + Matcher
//        檢核欄位資料不可為空
        if (!StringUtils.hasText(phone)) {
            member.setMessage("手機未輸入");
            member.setSuccessful(false);
            return member;
        }
        if (!StringUtils.hasText(pwd)) {
            member.setMessage("密碼未輸入");
            member.setSuccessful(false);
            return member;
        }
        if (!StringUtils.hasText(memberName)) {
            member.setMessage("姓名未輸入");
            member.setSuccessful(false);
            return member;
        }
//        確認是否已存在member
        if (isExistMember(member)) {
            member.setMessage("手機已註冊過");
            member.setSuccessful(false);
            return member;
        } else {
//        密碼加密
            String encodePwd = encrypSHA.SHAEncrypt(pwd);
            member.setCustomerPassword(encodePwd);
            LocalDate date = LocalDate.now();
            member.setCreateDate(Date.valueOf(date));
            member.setUpdateDate(Date.valueOf(date));

//            TODO - ADD VERIFY CODE  + updateDb
//            sendSmsService.sendSMS(phone,);

            return memberDao.createMember(member);
        }
    }

    @Override
    public Member login(Member member) {
        //todo need to revise + Matcher
        String phone = member.getCustomerPhone();
        String password = member.getCustomerPassword();

        if (!StringUtils.hasText(phone)) {
            member.setMessage("電話未輸入");
            member.setSuccessful(false);
            return member;
        }

        if (!StringUtils.hasText(password)) {
            member.setMessage("密碼未輸入");
            member.setSuccessful(false);
            return member;
        }
        String encodePwd = encrypSHA.SHAEncrypt(password);
        member.setCustomerPassword(encodePwd);
        System.out.println(password);
        System.out.println(encodePwd);
        member = memberDao.findMemberForLogin(phone, encodePwd);
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
        return !ObjectUtils.isEmpty(memberDao.findMemberByPhone(member.getCustomerPhone()));
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

    @Override
    public Integer updateMemberCarrier(Member member) {
        return memberDao.updateMemberCarrierById(member.getCustomerId(), member.getCustomerCarrier());
    }
}
