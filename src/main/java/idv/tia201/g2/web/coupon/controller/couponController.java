package idv.tia201.g2.web.coupon.controller;

import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coupon")
public class couponController {
    @Autowired
    private CouponService couponService;

    @PostMapping("save")
    public Coupon register(@RequestBody Coupon coupon) {


        return coupon;
    }
}
