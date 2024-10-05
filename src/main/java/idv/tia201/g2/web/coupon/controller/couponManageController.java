package idv.tia201.g2.web.coupon.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.coupon.service.CouponService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/coupon/manage")
public class couponManageController {
    @Autowired
    private CouponService couponService;

    //獲取目前登入使用者資料
    @GetMapping("/getrole")
    public TotalUserDTO getRole(HttpSession session){
        TotalUserDTO user = (TotalUserDTO) session.getAttribute("totalUserDTO");
        System.out.println("Session Attributes: " + Collections.list(session.getAttributeNames()));
        System.out.println("User from session: " + user);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in session");
        }
        return user;
    }
    @GetMapping
    public List<Coupon> getCoupons() {
        return couponService.findAllCoupons();
    }
//    @DeleteMapping("/delete/{couponId}")
//    public Core delete(@PathVariable Integer couponId){
//        final Core core = new Core();
//        if(couponId==null) {
//            core.setMessage("無id");
//            core.setSuccessful(false);
//        }else {
//            core.setMessage("無此商品ID資料:"+couponId);
//            core.setSuccessful(couponService.removeCoupon(couponId));
//        }
//        return core;
//    }
}
