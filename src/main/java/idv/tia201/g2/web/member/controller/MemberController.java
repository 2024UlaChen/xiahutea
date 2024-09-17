package idv.tia201.g2.web.member.controller;


import idv.tia201.g2.core.service.AddrService;
import idv.tia201.g2.core.vo.AddressDetailVo;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private AddrService addrService;

    @GetMapping("address/{memberId}")
    public List<MemberAddress> getMemberAddress(@PathVariable Integer memberId) {
        return memberService.findAddressByMemberId(memberId);
    }

    @GetMapping("carrier/{memberId}")
    public String getMemberCarrier(@PathVariable Integer memberId) {
        Member member = memberService.findMemberById(memberId);
        return member.getCustomerCarrier();
    }

    @GetMapping("address")
    public List<AddressDetailVo> getDefaultAddressList(){
        try {
            List<AddressDetailVo> addressDetailVoList = addrService.getAddrFromJson();
            System.out.println(addressDetailVoList);
            return addressDetailVoList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
