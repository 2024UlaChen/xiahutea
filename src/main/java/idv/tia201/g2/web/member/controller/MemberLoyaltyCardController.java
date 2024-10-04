package idv.tia201.g2.web.member.controller;


import idv.tia201.g2.web.member.service.MemberLoyaltyCardService;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member/loyalty")
public class MemberLoyaltyCardController {
    private MemberLoyaltyCardService memberLoyaltyCardService;

    @Autowired
    public MemberLoyaltyCardController(MemberLoyaltyCardService memberLoyaltyCardService) {
        this.memberLoyaltyCardService = memberLoyaltyCardService;
    }

@GetMapping("/info")
    public List<CustomerLoyaltyCard> getLoyaltyCard(HttpSession session) {
    TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");
    if(totalUserDTO == null) {return null;}
    return memberLoyaltyCardService.GetCustomerLoyaltyCards(totalUserDTO.getUserId());

}
}
