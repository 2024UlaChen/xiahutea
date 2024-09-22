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
        if (member == null || StringUtils.isEmpty(member.getCustomerPhone()) || StringUtils.isEmpty(member.getCustomerPassword())) {
            member.setMessage("input data wrong");
            member.setSuccessful(false);
            return member;
        }
        System.out.println(1);
        Member result = memberService.login(member);
        HttpSession httpSession = request.getSession();
        System.out.println(httpSession.getId());
        request.changeSessionId();
        System.out.println(httpSession.getId());
//        if (request.getSession(false) != null) {
//
//        }
//        System.out.println(session.getId());
//        System.out.println(model.getAttribute("Id"));
//        member.setCustomerPhone(phone);
//        member.setCustomerPassword(password);
//        member = memberService.login(member);

        return member;
    }
}
