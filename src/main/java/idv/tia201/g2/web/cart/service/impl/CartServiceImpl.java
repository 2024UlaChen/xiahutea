//package idv.tia201.g2.web.cart.service.impl;
//
//import idv.tia201.g2.web.cart.dao.CartDao;
//import idv.tia201.g2.web.cart.service.CartService;
//import idv.tia201.g2.web.member.vo.Cart;
//import idv.tia201.g2.web.product.dao.ProductDao;
//import idv.tia201.g2.web.product.vo.Product;
//import idv.tia201.g2.web.store.dao.StoreDao;
//import idv.tia201.g2.web.store.vo.Store;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class CartServiceImpl implements CartService {
//    @Autowired
//    private ProductDao productDao;
//    @Autowired
//    private StoreDao storeDao;
//    @Autowired
//    private CartDao cartDao;
//
//    //從前端讀入資料順便存一份到Cart table
////    @Override
////    public int insertCart(Integer productId) {
//        //TODO 取得Localstorage傳來的Product資料
////        Product product = productDao.getProductById(productId);
//        //TODO 抓localstorage設定的甜度、冰塊、加料
////        if(product != null){
////            Cart cart = new Cart();
////            cart.setProductId(product.getId());
////            cart.setProductId(product.getId());
////            cart.setUnitPrice(product.getProductPrice());
////            return cartDao.insertcart(cart);
////        }
////        return 0;
////    }
//
//        //用來抓取前端資料
////    @Override
////    public Map<String, Object> getProductAndStoreDetails(int productId) {
////        Product product = productDao.getProductById(productId);
////      Store store = storeDao.findById(product.getStoreId());
////         構建結果並返回
////        Map<String, Object> result = new HashMap<>();
//////        result.put("product", product);
////        result.put("store", store);
////        return result;
////    }
////    }
//}
//
