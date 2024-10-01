package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.core.util.ValidateUtil;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    @GetMapping("address/{memberId}")
    public List<MemberAddress> getMemberAddress(@PathVariable Integer memberId) {
        return memberService.findAddressByMemberId(memberId);
    }

    @PostMapping("address")
    public Core getMemberAddress(@RequestBody MemberAddress memberAddress) {
        Core core = new Core();
        if (memberAddress == null) {
            core.setMessage("no data , plz check");
            core.setSuccessful(false);
            return core;
        }
        core.setMessage("get address");
        core.setSuccessful(true);
        core.setData(memberService.findAddressByMemberId(memberAddress.getCustomerId()));
        return core;
    }

    @GetMapping("carrier/{memberId}")
    public String getMemberCarrier(@PathVariable Integer memberId) {
        Member member = memberService.findMemberById(memberId);
        return member.getCustomerCarrier();
    }

    @PostMapping("carrier")
    public Core updateMemberCarrier(@RequestBody Member member, @RequestParam(value = "type") String type) {
        Core core = new Core();
        String memberCarrier = member.getCustomerCarrier();

        if (type.equals("update")) {
            if (!StringUtils.hasText(memberCarrier) || !ValidateUtil.checkCarrier(memberCarrier)) {
                core.setMessage("carrier is wrong, plz check ");
                core.setSuccessful(false);
                return core;
            }
        }
        Integer updateResult = memberService.updateMemberCarrier(member);
        if (updateResult != 0) {
            core.setSuccessful(true);
            core.setMessage("update carrier successful");
        } else {
            core.setMessage("update carrier fail");
            core.setSuccessful(false);
        }
        return core;
    }

    @PostMapping("setting/check")
    public Core checkMemberPwd(@RequestBody Member member) {
        Core core = new Core();
        if (member == null || !StringUtils.hasText(member.getCustomerPassword())) {
            core.setSuccessful(false);
            core.setMessage("original pwd is wrong , plz check!");
            return core;
        } else {
            if (!memberService.checkMemberPwd(member.getCustomerId(), member.getCustomerPassword())) {
                core.setMessage("密碼錯誤");
                core.setSuccessful(false);
                return core;
            }
            core.setMessage("pwd check successful");
            core.setSuccessful(true);
            return core;
        }
    }

    @PostMapping("setting/update")
    public Core updateMemberPwd(@RequestBody Member member) {
        Core core = new Core();
        if (member == null || !StringUtils.hasText(member.getCustomerPassword())) {
            core.setSuccessful(false);
            core.setMessage("original pwd is wrong , plz check!");
            return core;
        } else {
            memberService.updateMemberPwd(member.getCustomerId(), member.getCustomerPassword());
            core.setMessage("更新成功");
            core.setSuccessful(true);
            return core;
        }
    }

    @PostMapping("address/update")
    public Core updateAddressByAddressId(@RequestBody MemberAddress memberAddress) {
        Core core = new Core();
        if (memberAddress == null) {
            core.setSuccessful(false);
            core.setMessage("data is wrong , plz check!");
            return core;
        }
        if (!memberService.saveMemberAddress(memberAddress)) {
            core.setSuccessful(false);
            core.setMessage("data is wrong , plz check!");
            return core;
        }
        core.setSuccessful(true);
        core.setMessage("success");
        return core;
    }

    @DeleteMapping("address/{customerAddressId}")
    public Core deleteAddressByAddressId(@PathVariable Integer customerAddressId) {
        Core core = new Core();
        if (customerAddressId == null) {
            core.setSuccessful(false);
            core.setMessage("data is wrong , plz check!");
            return core;
        }
        if (!memberService.deleteByMemberAddressId(customerAddressId)) {
            core.setSuccessful(false);
            core.setMessage("data is wrong , plz check!");
            return core;
        }
        core.setSuccessful(true);
        core.setMessage("已刪除");
        return core;
    }

    @PostMapping
    public Member getMemberInformation(HttpSession httpSession) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        Member member = new Member();
        if (totalUserDTO == null || null == totalUserDTO.getUserId()) {
            member.setSuccessful(false);
            member.setMessage("no memberId");
            return member;
        }
        member = memberService.findMemberById(totalUserDTO.getUserId());
        member.setSuccessful(true);
        System.out.println(member);
        return member;
    }

    @PostMapping("update")
    public Core updateMemberInfo(Member member) {
        Core core = new Core();
        if (member == null) {
            core.setSuccessful(false);
            core.setMessage("no member data");
            return core;
        }
//        memberService.
        return core;
    }
}
