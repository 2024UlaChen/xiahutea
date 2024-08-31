package idv.tia201.g2.web.cart.service;

import idv.tia201.g2.web.member.vo.Cart;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface CartService {

    //insert購物紀錄入Cart_item資料庫
    int insertCart(Integer productId);
    //用來抓取商品和商家資料透過商品id
    Map<String, Object> getProductAndStoreDetails(int productId);
}
