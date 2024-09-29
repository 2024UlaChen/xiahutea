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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.print.DocFlavor;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

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
//            發送驗證簡訊
//            String verifyCode = sendSmsService.sendSMS(phone);
            String verifyCode = "AAA123";
            member.setVerifyCode(verifyCode);
            LOGGER.info("register data: {}", member);
            memberDao.createMember(member);
            member = memberDao.findMemberByPhone(phone);
            member.setSuccessful(true);
            member.setMessage("註冊成功");
            return member;
        }
    }

    @Override
    public Member login(Member member) {
        //todo need to revise
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
        return memberDao.findMemberByPhone(member.getCustomerPhone()) != null;
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
//        TODO ADDRESS FORMAT
        if (memberAddress.getCustomerAddressId() == null || memberAddress.getCustomerId() == null || !StringUtils.hasText(memberAddress.getCustomerAddress())) {
            return false;
        }
        memberDao.updateMemberAddress(memberAddress);
        return true;
    }

    @Override
    public Boolean deleteByMemberAddressId(Integer customerAddressId) {
        try {
//            if (customerAddressId == null ) {
//                return false;
//            }
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
    public Boolean updateVerifyCode(Member member) {
        String phone = member.getCustomerPhone();
        if (member.getCustomerId() == null && !StringUtils.hasText(phone)) {
            return false;
        }
        if (member.getCustomerId() == null && StringUtils.hasText(phone)) {
            member = memberDao.findMemberByPhone(phone);
            if (member == null) {
                return false;
            }
        }
//        String verifyCode = sendSmsService.sendSMS(phone);
        String verifyCode = "B12345";

        memberDao.updateVerifyCodeById(member.getCustomerId(), verifyCode);
        return true;
    }

    @Override
    public Boolean isCorrectVerifyCode(Member member, String type) {
        Integer memberId = member.getCustomerId();
        String phone = member.getCustomerPhone();
        if (memberId == null && !StringUtils.hasText(phone)) {
            return false;
        }
        if (memberId == null && StringUtils.hasText(phone)) {
            Member memberResult = memberDao.findMemberByPhone(phone);
            if (memberResult == null) {
                return false;
            } else {
                memberId = memberResult.getCustomerId();
            }
        }
        Member queryMember = memberDao.findMemberById(memberId);
        String correctVerifyCode = queryMember.getVerifyCode();
        if (correctVerifyCode.equals(member.getVerifyCode())) {
//            if register => insert total user; if not equal register mean no need to insert totalUser
            if ("REGISTER".equals(type)) {
                TotalUsers totalUser = new TotalUsers(null, userType, queryMember.getCustomerId());
                totalUserDao.save(totalUser);
                memberDao.updateMemberInfo(queryMember.getCustomerId(), false, queryMember.getCustomerRemark());
            } else {
                String encodePwd = EncrypSHA.SHAEncrypt(member.getCustomerPassword());
                queryMember.setCustomerPassword(encodePwd);
                queryMember.setValidStatus(false);
                memberDao.updateMemberInfo(queryMember);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateMemberMoneyById(Integer memberId, Integer memberMoney) {
        if (memberId == null || memberMoney == null) {
            return false;
        } else {
            Integer result = memberDao.updateMemberMoney(memberId, memberMoney);
            return result != 0;
        }
    }

    @Override
    public Boolean checkMemberPwd(Integer memberId, String oldPwd) {
        String originalPwd = memberDao.findMemberById(memberId).getCustomerPassword();
        String encodePwd = EncrypSHA.SHAEncrypt(oldPwd);
        return encodePwd.equals(originalPwd);
    }

    @Override
    public void updateMemberPwd(Integer memberId, String newPwd) {
        String encodePwd = EncrypSHA.SHAEncrypt(newPwd);
        Member member = memberDao.findMemberById(memberId);
        member.setCustomerPassword(encodePwd);
        memberDao.updateMemberInfo(member);
    }
}
