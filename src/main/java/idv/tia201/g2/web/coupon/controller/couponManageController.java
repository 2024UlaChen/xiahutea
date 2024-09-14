package idv.tia201.g2.web.coupon.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon/manage")
public class couponManageController {
    @Autowired
    private CouponService couponService;

    @GetMapping
    public List<Coupon> getCoupons() {
        return couponService.findAllCoupons();
    }
    @DeleteMapping("/delete/{couponId}")
    public Core delete(@PathVariable Integer couponId){
        final Core core = new Core();
        if(couponId==null) {
            core.setMessage("無id");
            core.setSuccessful(false);
        }else {
            core.setSuccessful(couponService.removeCoupon(couponId));
        }
        return core;
    }
}
