package idv.tia201.g2.web.coupon.service.impl;

import idv.tia201.g2.web.coupon.dao.CouponDao;
import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponDao couponDao;

    @Override
    public Coupon saveCoupon(Coupon coupon) {
        return null;
    }
}
