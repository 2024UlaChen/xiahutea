package idv.tia201.g2.web.coupon.controller;

import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
