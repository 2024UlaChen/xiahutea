package idv.tia201.g2.web.coupon.service;

import idv.tia201.g2.web.coupon.vo.CustomerCoupons;

import java.sql.Timestamp;
import java.util.List;

public interface CustomerCouponService {
    public void updateCouponQuantity(Integer customerId, Integer couponId, Integer newQuantity);
    public List<CustomerCoupons> getCustomerCoupons(Integer customerId);
    public List<CustomerCoupons> getCustomerCouponsForShow(Integer customerId);
    public CustomerCoupons addResgiterCoupon(Integer customerId);
}
