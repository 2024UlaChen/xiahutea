package idv.tia201.g2.web.member.service;

import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;

import java.util.List;

public interface MemberLoyaltyCardService {
    CustomerLoyaltyCard UpdatePoints(Integer loyaltyCardId, Integer decreasePoints);

    CustomerLoyaltyCard GetCustomerLoyaltyCard(Integer loyaltyCardId);

    List<CustomerLoyaltyCard> GetCustomerLoyaltyCards(Integer memberId);

    boolean AddCustomerLoyaltyCard(CustomerLoyaltyCard customerLoyaltyCard);

    CustomerLoyaltyCard updateMemberStoreLoyaltyPoints(Integer storeId, Integer memberId, Integer totalMoney);
}
