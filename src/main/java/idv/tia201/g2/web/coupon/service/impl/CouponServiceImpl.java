package idv.tia201.g2.web.coupon.service.impl;

import idv.tia201.g2.web.coupon.dao.CouponDao;
import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    private final CouponDao couponDao;
    @Autowired
    public CouponServiceImpl(CouponDao couponDao) {
        this.couponDao = couponDao;
    }

    @Override
    public List<Coupon> getCoupons(List<Integer> Ids) {

        return couponDao.findAllById(Ids);

    }
}
