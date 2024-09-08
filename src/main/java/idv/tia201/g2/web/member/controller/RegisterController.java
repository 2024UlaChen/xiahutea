package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;

@Controller
@RequestMapping("member/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = -6064618207897871301L;
    @Autowired
    private MemberService memberService;

    @PostMapping
    public Member register(@RequestBody Member member) {
        if (member == null) {
            member = new Member();
            member.setMessage("no member data");
            member.setSuccessful(false);
            return member;
        }
        if(memberService.isExistMember(member)){
            member.setMessage("duplicate member data");
            member.setSuccessful(false);
            return member;
        }
        return memberService.register(member);
    }

}
