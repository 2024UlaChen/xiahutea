package idv.tia201.g2.web.coupon.controller;

import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class couponController {
    @Autowired
    private CouponService couponService;

    @PostMapping("/save")
    public Coupon register(@RequestBody Coupon coupon) {
        if(coupon == null) {
            coupon = new Coupon();
            coupon.setMessage("無優惠券資訊");
            coupon.setSuccessful(false);
            return coupon;
        }
        Coupon savedCoupon = couponService.saveCoupon(coupon);
//        System.out.println("CouponId: "+savedCoupon.getCouponId());
//        System.out.println("CouponName: "+savedCoupon.getCouponName());
//        System.out.println("Discount: "+savedCoupon.getDiscount());
//        System.out.println("CreateDate: "+savedCoupon.getCreateDate());
//        System.out.println("ExpiredDate: "+savedCoupon.getExpiredDate());
//        System.out.println("Message: "+savedCoupon.getMessage());
//        System.out.println("isSuccessful: "+savedCoupon.isSuccessful());
        return savedCoupon;
    }
}
