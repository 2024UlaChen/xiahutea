package idv.tia201.g2.web.coupon.dao;

import idv.tia201.g2.web.coupon.vo.CustomerCoupons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCoupon extends JpaRepository<CustomerCoupons,Integer> {
    public CustomerCoupons findByCustomerIdAndCouponId(Integer customerId, Integer couponId);
}
