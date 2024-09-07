package idv.tia201.g2.web.member.controller;


import idv.tia201.g2.web.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cms")
public class MemberCmsController {
    @Autowired
    private MemberService memberService;

    @GetMapping("manage")
    public String manage(Model model) {
        return "memberManage.html";
    }

}
