package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.core.util.ValidateUtil;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;


    @GetMapping("address/{memberId}")
    public List<MemberAddress> getMemberAddress(@PathVariable Integer memberId) {
        return memberService.findAddressByMemberId(memberId);
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
}
