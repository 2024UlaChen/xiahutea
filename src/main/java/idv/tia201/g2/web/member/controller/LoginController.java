package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member/login")
public class LoginController  {


    @Autowired
    private MemberService memberService;

    @PostMapping
    public Member Login(@RequestBody Member member, HttpServletRequest request) {
        if (member == null || !StringUtils.hasText(member.getCustomerPhone()) || !StringUtils.hasText(member.getCustomerPassword())) {
            member.setMessage("input data wrong");
            member.setSuccessful(false);
            return member;
        }
//        TODO - update session id
        HttpSession httpSession = request.getSession();
//        System.out.println(httpSession.getId());
        request.changeSessionId();
//        System.out.println(httpSession.getId());
        return memberService.login(member);
    }
}
