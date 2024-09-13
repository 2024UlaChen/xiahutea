package idv.tia201.g2.web.coupon.service.impl;

import idv.tia201.g2.web.coupon.dao.CouponDao;
import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponDao couponDao;

    @Override
    public Coupon saveCoupon(Coupon coupon) {
        if(coupon.getCouponName()==null){
            coupon.setMessage("優惠券標題未輸入");
            coupon.setSuccessful(false);
            return coupon;
        }
        if(couponDao.findByCouponName(coupon.getCouponName())!= null){
            coupon.setMessage("優惠券主題重複");
            coupon.setSuccessful(false);
            return coupon;
        }
        if(coupon.getCouponRule()==null){
            coupon.setMessage("優惠券內容未輸入");
            coupon.setSuccessful(false);
            return coupon;
        }
        if(coupon.getDiscount()==null){
            coupon.setMessage("優惠券未設定折扣");
            coupon.setSuccessful(false);
            return coupon;
        }
        if(coupon.getCreateDate()==null ||coupon.getExpiredDate()==null){
            coupon.setMessage("優惠券未設定期限");
            coupon.setSuccessful(false);
            return coupon;
        }
        if (coupon.getExpiredDate().before(coupon.getCreateDate())) {
            coupon.setMessage("優惠券結束日期不能小於開始日期");
            coupon.setSuccessful(false);
            return coupon;
        }
        coupon = couponDao.save(coupon);
        coupon.setSuccessful(true);
        return coupon;
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponDao.findAll();
    }
}
