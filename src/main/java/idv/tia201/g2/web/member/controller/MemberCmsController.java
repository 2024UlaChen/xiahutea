package idv.tia201.g2.web.member.controller;


import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cms/manage")
public class MemberCmsController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<Member> manage(Model model) {
        return memberService.findAllMember();
    }

    @GetMapping("{memberId}")
    public Member manage(Model model, @PathVariable Integer memberId) {
        return memberService.findMemberById(memberId);
    }


    @PostMapping
    public List<Member> manage(Model model, @RequestBody Member member) {
        if (member == null) {
            member = new Member();
            member.setMessage("no member data");
            member.setSuccessful(false);
            return null;
        }
//        TODO REVISE
        List<Member> memberList = new ArrayList<>();
        memberList = memberService.findQueryMember(member);
        for (Member m : memberList) {
            System.out.println(m);
        }
        return memberList;
    }

    @GetMapping("address/{memberId}")
    public List<MemberAddress> getMemberAddress(@PathVariable Integer memberId) {
        return memberService.findAddressByMemberId(memberId);
    }
}
