package idv.tia201.g2.web.cart.service;

import idv.tia201.g2.web.coupon.vo.Coupon;
import idv.tia201.g2.web.member.vo.Cart;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.member.vo.MemberAddress;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import idv.tia201.g2.web.store.vo.Store;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public interface CartService {

    //insert購物紀錄入Cart_item資料庫
//    int insertCart(Integer productId);
    //用來抓取商品和商家資料透過商品id
//    Map<String, Object> getProductAndStoreDetails(int productId);
    public void saveCartItems(List<Cart> cartItems);
    public List<Product> getProductsByIds(List<Integer> productIds);
    public Store getStoreById(Integer storeId);
    public Member findmemberById(Integer customerId);
    public List<MemberAddress> findAddressbyId(Integer customerId);
    public List<Coupon> findCouponsByCustomerId(Integer customerId);
    public CustomerLoyaltyCard findMemberLoyalCardById(Integer storeId, Integer customerId);
}
