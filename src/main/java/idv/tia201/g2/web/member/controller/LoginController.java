package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
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
            core.setMessage("input wrong data");
            core.setSuccessful(false);
            return core;
        }
//        TODO - update session id
        HttpSession httpSession = request.getSession();
//        System.out.println(httpSession.getId());
        request.changeSessionId();
//        System.out.println(httpSession.getId());
        member = memberService.login(member);

        if (!member.isSuccessful()) {
            core.setSuccessful(false);
            core.setMessage(member.getMessage());
            return core;
        }

        TotalUserDTO totalUserDTO = new TotalUserDTO();
        totalUserDTO.setTotalUserId((Long) member.getData());
        totalUserDTO.setUserId(member.getCustomerId());
        totalUserDTO.setUserTypeId(0);
        totalUserDTO.setLogo(member.getCustomerImg());

        httpSession.setAttribute("loggedin", true);
        httpSession.setAttribute("totalUserDTO", totalUserDTO);

        Map<String, Object> memberData = new HashMap<String, Object>();
        memberData.put("customerId", member.getCustomerId());
        memberData.put("customerImg", member.getCustomerImg());

        core.setData(memberData);
        core.setSuccessful(true);
        core.setMessage("登入成功");
        return core;
    }
}
