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
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    public byte[] loadImg() throws IOException {
        String imagePath = "static/img/userIcon.jpg";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(imagePath).getFile());
        return Files.readAllBytes(file.toPath());
    }

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
            try {
                member.setCustomerImg(loadImg());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        TotalUsers LoginTotalUser = totalUserDao.findByUserTypeIdAndUserId(0, member.getCustomerId());
        member.setData(LoginTotalUser.getTotalUserId());

        member.setMessage("登入成功");
        member.setSuccessful(true);
        return member;
    }

    @Override
    public Boolean editMember(Member member) {
        Member originalMember = memberDao.findMemberById(member.getCustomerId());
        originalMember.setBirthday(member.getBirthday());
        originalMember.setCustomerEmail(member.getCustomerEmail());
        originalMember.setSex(member.getSex());
        originalMember.setNickname(member.getNickname());
        LocalDate date = LocalDate.now();
        originalMember.setUpdateDate(Date.valueOf(date));
        return memberDao.updateMemberInfo(originalMember);
    }

    @Override
    public Member editMemberImg(Integer customerId, MultipartFile img) throws IOException {
        Member member = memberDao.findMemberById(customerId);
        member.setCustomerImg(img.getBytes());
        return memberDao.updateMember(member);
    }

    @Override
    public Boolean updateMemberByValidSatusAndCustomerRemark(Member member) {
        return memberDao.updateMemberInfo(member);
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
        Member member = memberDao.findMemberById(memberId);
        member.setSuccessful(true);
        return member;
    }

    @Override
    public List<Member> findMemberByValidStatus(Boolean memberValidStatus) {
        return memberDao.findMemberByValidStatus(memberValidStatus);
    }

    @Override
    public Page<Member> findQueryMember(Member member, int pageNo) {
//        動態查詢 - 電話 / id / 狀態 / name
        final int ONE_PAGE_SIZE = 6 ;

        String queryName = member.getNickname();
        String queryPhone = member.getCustomerPhone();
        Integer queryId = member.getCustomerId();
        Boolean queryStatus = member.getValidStatus();
        List<Member> totalList = memberDao.findMemberByQueryParam(queryName, queryId, queryPhone, queryStatus, pageNo);

        Pageable pageRequest = PageRequest.of(pageNo, ONE_PAGE_SIZE);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), totalList.size());
        List<Member> pageContent = totalList.subList(start, end);
        Page<Member> nowPage = new PageImpl<>(pageContent, pageRequest, totalList.size());
        return nowPage;

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
