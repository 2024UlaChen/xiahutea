package idv.tia201.g2.web.coupon.dao;

import idv.tia201.g2.web.coupon.vo.CustomerCoupons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface CustomerCouponDao extends JpaRepository<CustomerCoupons,Integer> {
    public CustomerCoupons findByCustomerIdAndCouponId(Integer customerId, Integer couponId);
    public CustomerCoupons findByCustomerIdAndCustomerCouponsId(Integer customerId, Integer customerCouponId);
    public List<CustomerCoupons> findByCustomerId(Integer customerId);

    List<CustomerCoupons> findByCustomerIdAndCouponQuantityGreaterThan(Integer customerId, Integer couponQuantity);
    List<CustomerCoupons> findByCustomerIdAndCouponQuantityGreaterThanAndCoupon_ExpiredDateGreaterThan(Integer customerId, Integer couponQuantity, Timestamp expiredDate);
    List<CustomerCoupons> findByCustomerIdAndCouponQuantityGreaterThanAndCoupon_ExpiredDateGreaterThanAndCoupon_CouponStatus(Integer customerId, Integer couponQuantity, Timestamp expiredDate,boolean status);
}
