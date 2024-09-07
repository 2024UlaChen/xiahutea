package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.web.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = -2956734137070898975L;
    @Autowired
    private MemberService memberService;

}
