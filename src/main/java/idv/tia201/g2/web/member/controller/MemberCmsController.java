package idv.tia201.g2.web.member.controller;


import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cms/manage")
public class MemberCmsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberCmsController.class);
    @Autowired
    private MemberService memberService;

    @GetMapping("all")
    public List<Member> manage(HttpSession httpSession) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        if (totalUserDTO.getUserTypeId() != 3) {
            List<Member> member = new ArrayList<>();
            return member;
        }
        return memberService.findAllMember();
    }

    @PostMapping("detail")
    public Core setMemberSession(HttpSession httpSession, @RequestBody Member member) {
        Core core = new Core();
        if (member == null || member.getCustomerId() == null) {
            core.setSuccessful(false);
            core.setMessage("data error");
            return core;
        }
        httpSession.setAttribute("cmsMemberDetails", member);
        core.setSuccessful(true);
        return core;
    }

    @GetMapping("detail")
    public Member getMemberDetail(HttpSession httpSession) {
        Member member = (Member) httpSession.getAttribute("cmsMemberDetails");
        if (member == null || member.getCustomerId() == null) {
            member.setSuccessful(false);
            member.setMessage("data error");
            return member;
        }
        member = memberService.findMemberById(member.getCustomerId());
        if (!member.isSuccessful()) {
            member.setSuccessful(false);
            member.setMessage("get member data error");
            return member;
        }
        httpSession.setAttribute("cmsMemberDetails", member);
        return member;
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

    @GetMapping("address")
    public List<MemberAddress> getMemberAddress(HttpSession httpSession) {
        Member member = (Member) httpSession.getAttribute("cmsMemberDetails");
        return memberService.findAddressByMemberId(member.getCustomerId());
    }

    @PostMapping("update")
    public Core updateMember(@RequestBody Member member, HttpSession httpSession) {
        final Core core = new Core();
        Member sessionMember = (Member) httpSession.getAttribute("cmsMemberDetails");
        sessionMember.setValidStatus(member.getValidStatus());
        sessionMember.setCustomerRemark(member.getCustomerRemark());

        if (!memberService.updateMemberByValidSatusAndCustomerRemark(sessionMember)) {
            core.setSuccessful(false);
            core.setMessage("update fail");
        }
        core.setSuccessful(true);
        core.setMessage("update success");
        return core;
    }
}
