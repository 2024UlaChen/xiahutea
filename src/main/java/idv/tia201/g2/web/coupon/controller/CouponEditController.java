package idv.tia201.g2.web.coupon.controller;

import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.service.CustomerCouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import idv.tia201.g2.web.coupon.vo.CustomerCoupons;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponEditController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private CustomerCouponService customerCouponService;

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
    @GetMapping("/edit/{couponId}")
    public Coupon edit(@PathVariable Integer couponId) {
        return couponService.findCouponById(couponId);
    }

    @GetMapping("/list")
    public List<CustomerCoupons> GetCustomerCoupon(HttpSession session) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");
        if(totalUserDTO == null) {return null;}
        return customerCouponService.getCustomerCouponsForShow(totalUserDTO.getUserId());
    }

}