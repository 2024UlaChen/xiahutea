package idv.tia201.g2.web.cart.controller;

import idv.tia201.g2.web.cart.service.CartService;
import idv.tia201.g2.web.coupon.vo.Coupon;
import idv.tia201.g2.web.coupon.vo.CustomerCoupons;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController  {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // GET 請求：進入購物結帳頁面取得使用者
    @GetMapping("/checkoutlist/{customerId}")
        public Member getMember(@PathVariable int customerId) {
            return cartService.findmemberById(customerId);
    }
    // GET 請求：取得使用者常用地址
    @GetMapping("/checkoutlist/Memberaddress/{customerId}")
    public List<MemberAddress> getMemberAddress(@PathVariable int customerId) {
        return cartService.findAddressbyId(customerId);
    }
    // GET 請求：取得使用者的優惠券
    @GetMapping("/getCoupon/{customerId}")
    public List<Coupon> getCoupons(@PathVariable int customerId){
        return cartService.findCouponsByCustomerId(customerId);
    }
    // GET 請求：取得使用者的使用者優惠券ID
    @GetMapping("/getCustomerCoupon/{customerId}/{couponId}")
    public CustomerCoupons getCustomerCoupons(@PathVariable Integer customerId,@PathVariable Integer couponId){
        return cartService.findCustomerCoupons(customerId,couponId);
    }

    // POST 請求：獲取商品資料
    @PostMapping("/checkoutlist/findByproductIds")
    @ResponseBody
    public List<Product> findProductsByIds(@RequestBody List<Integer> productIds) {
        return cartService.getProductsByIds(productIds);
    }
    // POST 請求：獲取商店資料
    @PostMapping("/checkoutlist/findByStoreId")
    @ResponseBody
    public Store findstoreById(@RequestBody Integer storeId) {
        return cartService.getStoreById(storeId);
    }
    // POST 請求：儲存購物車資料
//    @PostMapping("/checkoutlist/saveItems")
//    @ResponseBody
//    public String saveCartItems(@RequestBody List<Cart> cartItems) {
//        // 儲存購物車資料的邏輯
//        cartService.saveCartItems(cartItems);
//        return "Cart items saved";
//    }
    //GET 請求:獲得會員卡餘額
    @GetMapping("/checkoutlist/getMemberCard/{storeId}/{customerId}")
    public CustomerLoyaltyCard getMemberCard(@PathVariable int storeId, @PathVariable int customerId) {
        return cartService.findMemberLoyalCardById(storeId, customerId);
    }
}

