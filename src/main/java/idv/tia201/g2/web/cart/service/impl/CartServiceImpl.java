package idv.tia201.g2.web.cart.service.impl;

import idv.tia201.g2.web.cart.service.CartService;
import idv.tia201.g2.web.cart.dao.CartDao;
import idv.tia201.g2.web.coupon.dao.CouponDao;
import idv.tia201.g2.web.coupon.dao.CustomerCouponDao;
import idv.tia201.g2.web.coupon.vo.Coupon;
import idv.tia201.g2.web.coupon.vo.CustomerCoupons;
import idv.tia201.g2.web.member.dao.MemberAddrDao;
import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.dao.MemberLoyaltyCardRepository;
import idv.tia201.g2.web.member.vo.MemberAddress;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.member.vo.Cart;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.product.vo.Product;

import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private CouponDao couponDao;
    @Autowired
    private CustomerCouponDao customerCouponDao;
    @Autowired
    private MemberAddrDao memberAddrDao;
    @Autowired
    private MemberLoyaltyCardRepository memberLoyaltyCarddao;

    //抓取會員資料
    @Override
    public Member findmemberById(Integer customerId) {
        return memberDao.findMemberById(customerId);
    }

    //批量insert cart資料
    public void saveCartItems(List<Cart> cartItems) {
        cartDao.saveAll(cartItems);
    }

    //透過商品ID抓取product
    @Override
    public List<Product> getProductsByIds(List<Integer> productIds) {
        return productDao.findAllById(productIds);
    }

    //透過商店ID抓取store
    @Override
    public Store getStoreById(Integer storeId) {
        return storeDao.findByStoreId(storeId);
    }

//    @Override
    public List<Coupon> findCouponsByCustomerId(Integer customerId) {
        return couponDao.findCouponIdsByCutomerId(customerId);
    }

    @Override
    public CustomerCoupons findCustomerCoupons(Integer customerId, Integer couponId) {
        return customerCouponDao.findByCustomerIdAndCouponId(customerId,couponId);
    }

    @Override
    public CustomerLoyaltyCard findMemberLoyalCardById(Integer storeId, Integer customerId ) {
       return memberLoyaltyCarddao.findByStoreIdAndMemberId(storeId,customerId);
    }

    @Override
    public List<MemberAddress> findAddressbyId(Integer customerId) {
        return memberAddrDao.findAddressByMemberId(customerId);
    }
}


