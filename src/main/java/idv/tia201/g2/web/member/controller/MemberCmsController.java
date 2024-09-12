package idv.tia201.g2.web.member.controller;


import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("cms")
public class MemberCmsController {
    @Autowired
    private MemberService memberService;

    @GetMapping("manage")
    public List<Member> manage(Model model) {

        List<Member> memberList = memberService.findAllMember();
        for(Member member:memberList){
            System.out.println(member);
        }
        return memberList;
    }
}
