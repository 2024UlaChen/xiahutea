package idv.tia201.g2.web.coupon.service;

public interface CustomerCouponService {
    public void updateCouponQuantity(Integer customerId, Integer couponId, Integer newQuantity);
}
