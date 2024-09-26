package idv.tia201.g2.web.member.service;

import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;

public interface MemberLoyaltyCardService {
    CustomerLoyaltyCard UpdatePoints(Integer loyaltyCardId, Integer decreasePoints);

    CustomerLoyaltyCard GetCustomerLoyaltyCard(Integer loyaltyCardId);

    boolean AddCustomerLoyaltyCard(CustomerLoyaltyCard customerLoyaltyCard);
}
