package idv.tia201.g2.web.member.controller;


import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("address/{memberId}")
    public List<MemberAddress> getMemberAddress(@PathVariable Integer memberId) {
        return memberService.findAddressByMemberId(memberId);
    }

}
