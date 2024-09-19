package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("member/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = -2956734137070898975L;
    @Autowired
    private MemberService memberService;

    @GetMapping("{phone}/{password}")
    public Member Login( @PathVariable String phone, @PathVariable String password) {
        //todo follow - need to revise

        Member member = new Member();
        if (password == null || phone == null) {
            member.setMessage("無會員資訊");
            member.setSuccessful(false);
            return member;
        }
        member.setCustomerPhone(phone);
        member.setCustomerPassword(password);
        member = memberService.login(member);
//        if (member.isSuccessful()) {
//            if (request.getSession(false) != null) {
//                request.changeSessionId();
//            }
//            final HttpSession session = request.getSession();
//            session.setAttribute("loggedIn", true);
//            session.setAttribute("member", member);
//        }
        return member;
    }
}
