package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("member/register")
public class RegisterController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public Core register(@RequestBody Member member) {
        Core core = new Core();
        if (member == null) {
            core.setMessage("register - no member data");
            core.setSuccessful(false);
            return core;
        }
        Member memberResult = memberService.register(member);
        if(!memberResult.isSuccessful()){
            core.setMessage(memberResult.getMessage());
            core.setSuccessful(false);
            return core;
        }
//        TODO CHECK WHY IS NULL
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("customerId", memberResult.getCustomerId());
        core.setData(map);
        core.setSuccessful(true);
        return core;
    }

    @PostMapping("check/{type}")
    public Core checkVerifyCode(@RequestBody Member member, @PathVariable String type) {
        //check verify code is match db or not
        Core core = new Core();
        if (member == null) {
            core.setSuccessful(false);
            core.setMessage("input data error");
            return core;
        }
        if (memberService.isCorrectVerifyCode(member, type)) {
            core.setSuccessful(true);
            core.setMessage("verify code is correct");
        } else {
            core.setSuccessful(false);
            core.setMessage("verify code is incorrect");
        }
        return core;
    }

    @PostMapping("update")
    public Core getNewVerifyCode(@RequestBody Member member) {
        //    重新取得verifyCode
        Core core = new Core();
        if (member == null) {
            core.setSuccessful(false);
            core.setMessage("input data error");
            return core;
        }
        if (memberService.updateVerifyCode(member)) {
            core.setSuccessful(true);
            core.setMessage("re-send verify code");
        } else {
            core.setSuccessful(false);
            core.setMessage("re-send verify code error");
        }
        return core;
    }

}
