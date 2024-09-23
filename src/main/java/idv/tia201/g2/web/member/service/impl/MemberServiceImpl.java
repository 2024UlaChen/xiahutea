package idv.tia201.g2.web.member.service.impl;

import idv.tia201.g2.core.util.EncrypSHA;
import idv.tia201.g2.core.util.ValidateUtil;
import idv.tia201.g2.web.member.dao.MemberAddrDao;
import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import idv.tia201.g2.web.user.dao.TotalUserDao;
import idv.tia201.g2.web.user.vo.TotalUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Date;
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
    TotalUserDao totalUserDao;

    @Autowired
    private SendSmsService sendSmsService;

    Integer userType = 0;


    @Override
    public Member register(Member member) {
        String phone = member.getCustomerPhone();
        String pwd = member.getCustomerPassword();
        String memberName = member.getNickname();
//        檢核欄位資料
        if (!StringUtils.hasText(phone) || !ValidateUtil.checkCellphone(phone)) {
            member.setMessage("手機未輸入或格式錯誤");
            member.setSuccessful(false);
            return member;
        }
        if (!StringUtils.hasText(pwd) || !ValidateUtil.checkMemberPwd(pwd)) {
            member.setMessage("密碼未輸入或格式錯誤");
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
            String encodePwd = EncrypSHA.SHAEncrypt(pwd);
            member.setCustomerPassword(encodePwd);
            LocalDate date = LocalDate.now();
            member.setCreateDate(Date.valueOf(date));
            member.setUpdateDate(Date.valueOf(date));
//            String verifyCode = sendSmsService.sendSMS(phone);
            String verifyCode = "AAA123";
            member.setVerifyCode(verifyCode);
            memberDao.createMember(member);
            return memberDao.findMemberByPhone(phone);
        }
    }

    @Override
    public Boolean updateVerifyCode(Member member) {
        if (member.getCustomerId() == null) {
            return false;
        }
//            String verifyCode = sendSmsService.sendSMS(phone);
        String verifyCode = "B12345";
        memberDao.updateVerifyCodeById(member.getCustomerId(), verifyCode);
        return true;
    }


    @Override
    public Member login(Member member) {
        //todo need to revise + Matcher
        String phone = member.getCustomerPhone();
        String password = member.getCustomerPassword();

        if (!StringUtils.hasText(phone) || !ValidateUtil.checkCellphone(phone)) {
            member.setMessage("手機未輸入或格式錯誤");
            member.setSuccessful(false);
            return member;
        }

        if (!StringUtils.hasText(password) || !ValidateUtil.checkMemberPwd(password)) {
            member.setMessage("密碼未輸入或格式錯誤");
            member.setSuccessful(false);
            return member;
        }
        String encodePwd = EncrypSHA.SHAEncrypt(password);
        member.setCustomerPassword(encodePwd);
        System.out.println(password);
        System.out.println(encodePwd);
        member = memberDao.findMemberForLogin(phone, encodePwd);
        if (member == null) {
            member = new Member();
            member.setMessage("使用者手機或密碼錯誤");
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
        } else if (StringUtils.hasText(member.getNickname())) {
            return memberDao.findMemberByNickname(member.getNickname());
        } else {
            List<Member> memberResultList = new ArrayList<>();
            if (StringUtils.hasText(member.getCustomerPhone())) {
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

    @Override
    public Boolean isCorrectVerifyCode(Member member) {
        Integer memberId = member.getCustomerId();
        if (memberId == null) {
            return false;
        }
        Member queryMember = memberDao.findMemberById(memberId);
        String correctVerifyCode = queryMember.getVerifyCode();
        if (correctVerifyCode.equals(member.getVerifyCode())) {
//            TODO NEED TO CHECK TOTAL USER INSERT
            TotalUsers totalUser = new TotalUsers(null, userType, queryMember.getCustomerId(),queryMember.getNickname(),queryMember.getCustomerImg());

            totalUserDao.save(totalUser);
            memberDao.updateMemberInfo(queryMember.getCustomerId(),false,queryMember.getCustomerRemark());
            return true;
        } else {
            return false;
        }
    }
}
