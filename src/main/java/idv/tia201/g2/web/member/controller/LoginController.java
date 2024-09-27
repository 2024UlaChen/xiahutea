package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("member/login")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private MemberService memberService;

    @PostMapping
    public Core Login(@RequestBody Member member, HttpServletRequest request) {
        LOGGER.info("Login Start,{}", member);

        Core core = new Core();
        if (member == null || !StringUtils.hasText(member.getCustomerPhone()) || !StringUtils.hasText(member.getCustomerPassword())) {
            core.setMessage("input data wrong");
            core.setSuccessful(false);
            return core;
        }
//        TODO - update session id
        HttpSession httpSession = request.getSession();
//        System.out.println(httpSession.getId());
        request.changeSessionId();
//        System.out.println(httpSession.getId());
        member = memberService.login(member);
        Map<String, Object> memberData = new HashMap<String, Object>();
        memberData.put("customerId", member.getCustomerId());
        memberData.put("customerImg", member.getCustomerImg());

        core.setData(memberData);
        core.setSuccessful(true);
//        System.out.println(core);
        return core;
    }
}
