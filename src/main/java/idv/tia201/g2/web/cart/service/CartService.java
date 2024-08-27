package idv.tia201.g2.web.cart.service;

import idv.tia201.g2.web.member.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;

public interface CartService {

    Cart getProductDetail(Integer productId);
}
