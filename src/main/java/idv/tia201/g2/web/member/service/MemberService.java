package idv.tia201.g2.web.member.service;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;

import java.util.List;

public interface MemberService {
    //    註冊，先檢查是否存在，若沒有再註冊
    Member register(Member member);

    //    登入
    Member login(Member member);

    //    編輯
    Member editMember(Member member);

    //    編輯地址
    MemberAddress editMemberAddress(MemberAddress memberAddress);

    //    列出所有member
    List<Member> findAllMember();

    //    判斷是否存在會員
    boolean isExistMember(Member member);

    //取得單一會員資料
    Member getMemberInfo(Integer memberId);
    //    列出所有會員地址
    List<MemberAddress> findAllMemberAdress();

    //    儲存
    boolean saveMember(Member member);

    //    存地址
    boolean saveMemberAddress(MemberAddress memberAddress);

    //    刪地址
    boolean deleteByMemberAddressId(Integer customerAddressId);


}
