package idv.tia201.g2.web.member.controller;


import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cms/manage")
public class MemberCmsController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<Member> manage(Model model) {
        List<Member> memberList = memberService.findAllMember();
//        model.addAttribute("memberList", memberList);
        for (Member member : memberList) {
            System.out.println(member);
        }
        return memberList;
    }
    @GetMapping("{memberId}")
    public Member manage(Model model, @PathVariable Integer memberId) {
        Member member = memberService.findMemberById(memberId);
        System.out.println(member);
//        model.addAttribute("member", member);
        return member;
    }


//    @PostMapping
//    public List<Member> manage(Model model, @RequestBody Member member) {
//        if (member == null) {
//            member = new Member();
//            member.setMessage("no member data");
//            member.setSuccessful(false);
//            return member;
//        }
//        System.out.println(member);
//        if(member.getValidStatus()!=null){
//            memberService.findMemberByValidStatus(member.getValidStatus());
//        }
////        Member member = memberService.findMemberById(memberId);
////        System.out.println(member);
////        model.addAttribute("member", member);
//        return null;
//    }
}
