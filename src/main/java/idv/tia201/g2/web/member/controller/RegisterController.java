package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.service.impl.SendSmsService;
import idv.tia201.g2.web.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member/register")
public class RegisterController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public Core register(@RequestBody Member member) {
        Core core = new Core();
        if (member == null) {
            member = new Member();
            member.setMessage("register - no member data");
            member.setSuccessful(false);
            return member;
        }
        Member memberResult = memberService.register(member);
        System.out.println(memberResult);

        return memberResult;
    }

    @PostMapping("check")
    public Core checkVerifyCode(@RequestBody Member member) {
        Core core = new Core();
        if (member == null) {
            core.setSuccessful(false);
            core.setMessage("input data error");
            return core;
        }
        if (memberService.isCorrectVerifyCode(member)) {
            core.setSuccessful(false);
            core.setMessage("verify code is correct");
        } else {
            core.setSuccessful(false);
            core.setMessage("verify code is incorrect");
        }
        return core;
    }

}
