package idv.tia201.g2.web.cart;

import idv.tia201.g2.web.cart.service.CartService;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@RestController
@RequestMapping("cart")
public class getCartItemDetail extends HttpServlet {

    @Autowired
    private CartService cartService;
//    @GetMapping("/cart/cartitem")
//    public Product getCartProducts(){
//        cartService.getProductAndStoreDetails()
//    }
}
