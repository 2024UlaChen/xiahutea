package idv.tia201.g2.web.cart.service.impl;

import idv.tia201.g2.web.cart.service.CartService;
import idv.tia201.g2.web.member.vo.Cart;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Cart selectProductDetail(Integer productId) {
        Product product = productDao.getProductById(productId);

        return null;
    }
}
