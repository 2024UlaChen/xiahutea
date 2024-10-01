package idv.tia201.g2.web.store.dao;

import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLoyaltCardRepository extends JpaRepository<CustomerLoyaltyCard,Integer> {
}
