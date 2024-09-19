package idv.tia201.g2.web.member.controller;


import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cms/manage")
public class MemberCmsController {
    private static final Logger log = LoggerFactory.getLogger(MemberCmsController.class);
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
        return memberService.findQueryMember(member);
    }

    @GetMapping("address/{memberId}")
    public List<MemberAddress> getMemberAddress(@PathVariable Integer memberId) {
        return memberService.findAddressByMemberId(memberId);
    }

    //TODO - patch不可以參數不可以為null
    @PatchMapping("{memberId}")
    public Core updateMember(@PathVariable Integer memberId, @RequestBody Member member) {
        final Core core = new Core();
        member.setCustomerId(memberId);
        if (memberService.updateMemberByValidSatusAndCustomerRemark(member) != 0) {
            core.setSuccessful(true);
            core.setMessage("update success");
        } else {
            core.setSuccessful(false);
            core.setMessage("update fail");
        }
        return core;
    }
}
