package idv.tia201.g2.web.coupon.service;

import idv.tia201.g2.web.coupon.vo.CustomerCoupons;

import java.util.List;

public interface CustomerCouponService {
    public void updateCouponQuantity(Integer customerId, Integer couponId, Integer newQuantity);
    public List<CustomerCoupons> getCustomerCoupons(Integer customerId);
}
