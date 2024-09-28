package idv.tia201.g2.web.member.service.impl;

import idv.tia201.g2.web.member.dao.MemberLoyaltyCardRepository;
import idv.tia201.g2.web.member.service.MemberLoyaltyCardService;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberLoyaltyCardServiceImpl implements MemberLoyaltyCardService {


    private MemberLoyaltyCardRepository memberLoyaltyCardRepository;
    @Autowired
    public MemberLoyaltyCardServiceImpl(MemberLoyaltyCardRepository memberLoyaltyCardRepository) {
        this.memberLoyaltyCardRepository = memberLoyaltyCardRepository;
    }

    @Override
    public CustomerLoyaltyCard UpdatePoints(Integer loyaltyCardId, Integer decreasePoints) {
        CustomerLoyaltyCard data = GetCustomerLoyaltyCard(loyaltyCardId);
        Integer newPoints = data.getPoints() - decreasePoints;
        data.setPoints(newPoints);
        return memberLoyaltyCardRepository.save(data);

    }

    @Override
    public CustomerLoyaltyCard GetCustomerLoyaltyCard(Integer loyaltyCardId) {
        return memberLoyaltyCardRepository.findByLoyaltyCardId(loyaltyCardId);
    }

    @Override
    public boolean AddCustomerLoyaltyCard(CustomerLoyaltyCard customerLoyaltyCard) {
        //確認是否是新增
        CustomerLoyaltyCard data = memberLoyaltyCardRepository.findByStoreIdAndMemberId(customerLoyaltyCard.getStoreId(), customerLoyaltyCard.getMemberId());
        if (data != null) {return false;}
        memberLoyaltyCardRepository.save(customerLoyaltyCard);
        return true;
    }
}
