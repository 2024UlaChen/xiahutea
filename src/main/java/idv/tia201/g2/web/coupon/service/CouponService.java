package idv.tia201.g2.web.coupon.service;

import idv.tia201.g2.web.coupon.vo.Coupon;

import java.util.List;

public interface CouponService {

    List<Coupon> getCoupons(List<Integer> Ids);
}
