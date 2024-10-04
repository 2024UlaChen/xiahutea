package idv.tia201.g2.web.member.service;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    //    註冊，先檢查是否存在，若沒有再註冊
    Member register(Member member);

    //    登入
    Member login(Member member);

    //    編輯
    Member editMemberImg(Integer customerId, MultipartFile img) throws IOException;

    Boolean editMember(Member member);

    Integer updateMemberCarrier(Member member);

    Boolean updateMemberByValidSatusAndCustomerRemark(Member member);

    //    編輯地址
    MemberAddress editMemberAddress(MemberAddress memberAddress);

    //    列出所有member
    List<Member> findAllMember();

    Page<Member> findQueryMember(Member member, int pageNo);

    List<Member> findMemberByValidStatus(Boolean memberValidStatus);

    //    判斷是否存在會員
    Boolean isExistMember(Member member);

    //取得單一會員資料
    Member findMemberById(Integer memberId);

    //判斷verifyCode是否相同
    Boolean isCorrectVerifyCode(Member member, String type);

    Boolean updateVerifyCode(Member member);

    //    列出所有會員地址
    List<MemberAddress> findAddressByMemberId(Integer memberId);

    //    儲存
    Boolean saveMember(Member member);

    //    存地址
    Boolean saveMemberAddress(MemberAddress memberAddress);

    //    刪地址
    Boolean deleteByMemberAddressId(Integer customerAddressId);

    Boolean updateMemberMoneyById(Integer memberId, Integer memberMoney);

    void updateMemberPwd(Integer memberId, String newPwd);

    Boolean checkMemberPwd(Integer memberId, String oldPwd);


}
