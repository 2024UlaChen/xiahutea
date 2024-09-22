package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member/register")
public class RegisterController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public Member register(@RequestBody Member member) {
        if (member == null) {
            member = new Member();
            member.setMessage("register - no member data");
            member.setSuccessful(false);
            return member;
        }
        Member memberResult = memberService.register(member);
        return memberResult;
    }

    @PostMapping("check")
    public Boolean checkVerifyCode(@RequestParam String phone, @RequestParam String verifyCode) {

        return true;
    }

}
