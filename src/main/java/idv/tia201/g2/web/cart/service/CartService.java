package idv.tia201.g2.web.cart.service;

import idv.tia201.g2.web.member.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;

public interface CartService {

    //點選修改品項時燈箱使用
    Cart selectProductDetail(Integer productId);
}
