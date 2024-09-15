package idv.tia201.g2.web.coupon.service;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.coupon.vo.Coupon;

import java.util.List;

public interface CouponService {
    public Coupon saveCoupon(Coupon coupon);
    public List<Coupon> findAllCoupons();
    public Coupon findCouponById(Integer id);
//    public boolean removeCoupon(Integer couponId);
}
