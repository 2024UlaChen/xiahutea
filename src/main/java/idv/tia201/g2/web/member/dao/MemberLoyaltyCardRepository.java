package idv.tia201.g2.web.member.dao;

import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLoyaltyCardRepository extends JpaRepository<CustomerLoyaltyCard,Integer> {
    CustomerLoyaltyCard findByStoreIdAndMemberId(Integer storeId, Integer memberId);
    CustomerLoyaltyCard findByLoyaltyCardId(Integer loyaltyCardId);
    CustomerLoyaltyCard findByCustomerIdAAndStoreId(Integer customerId, Integer storeId);


}
