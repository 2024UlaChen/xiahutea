package idv.tia201.g2.web.cart.service.impl;

import idv.tia201.g2.web.cart.service.CartService;
import idv.tia201.g2.web.cart.dao.CartDao;
import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.member.vo.Cart;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.product.vo.Product;

import idv.tia201.g2.web.store.dao.StoreDao;
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
//    @Autowired
//    private MemberDao memberDao;

    //抓取會員資料
    //    @Override
    //    public Member getCustomerinfo(Integer customerID) {
    //        return memberDao.findByMemberId(customerID);
    //    }

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
    public Store getStoreByid(Integer storeId) {
        return storeDao.findByStoreId(storeId);
    }
}


