package idv.tia201.g2.web.cart.dao;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.member.vo.Cart;

public interface CartDao {
    Cart selectByProductId(Integer id);

}
