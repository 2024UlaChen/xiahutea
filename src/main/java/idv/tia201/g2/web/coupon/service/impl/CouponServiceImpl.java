package idv.tia201.g2.web.coupon.service.impl;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.coupon.dao.CouponDao;
import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.service.CustomerCouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponDao couponDao;
    @Autowired
    private CustomerCouponService customerCouponService;

    @Override
    public Coupon saveCoupon(Coupon coupon) {
        if(coupon.getCouponName()==null){
            coupon.setMessage("優惠券標題未輸入");
            coupon.setSuccessful(false);
            return coupon;
        }
        if (coupon.getCouponId() == null) {
            if (couponDao.findByCouponName(coupon.getCouponName()) != null) {
                coupon.setMessage("優惠券主題重複");
                coupon.setSuccessful(false);
                return coupon;
            }
        } else {
            // 編輯情形：檢查優惠券名稱是否重複，但排除當前編輯的優惠券
            Coupon existingCoupon = couponDao.findByCouponName(coupon.getCouponName());
            if (existingCoupon != null && !existingCoupon.getCouponId().equals(coupon.getCouponId())) {
                coupon.setMessage("優惠券主題重複");
                coupon.setSuccessful(false);
                return coupon;
            }
        }
//        if(couponDao.findByCouponName(coupon.getCouponName())!= null){
//            coupon.setMessage("優惠券主題重複");
//            coupon.setSuccessful(false);
//            return coupon;
//        }
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
//        System.out.println("couponID:"+coupon.getCouponId());
        if (coupon.getCouponId() == null){
//            System.out.println("進入新增");
            coupon = couponDao.save(coupon);
            coupon.setSuccessful(true);
            customerCouponService.SendCouponsToMembers(coupon.getCouponId());
        }else{
//            System.out.println("進入編輯");
            coupon = couponDao.save(coupon);
            coupon.setSuccessful(true);
        }
        return coupon;
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponDao.findAll(Sort.by(Sort.Direction.DESC, "couponId"));
    }

    @Override
    public Coupon findCouponById(Integer couponId) {
//        return couponDao.findById(couponId)
//                .orElseThrow(() -> new EntityNotFoundException("Coupon not found with id: " + couponId));
        return couponDao.findById(couponId)
                .orElse(new Coupon());
    }

}
