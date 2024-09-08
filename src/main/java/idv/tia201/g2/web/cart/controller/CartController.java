package idv.tia201.g2.web.cart;

import idv.tia201.g2.web.cart.service.CartService;
import idv.tia201.g2.web.member.vo.Cart;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController extends HttpServlet {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // GET 請求：進入購物結帳頁面取得使用者
//    @GetMapping("/checkoutlist/{customerId}")
//    public String checkoutPage(@PathVariable int customerId, Model model) {
//        // 可以在這裡傳送一些初始資料，例如顧客資料等
//        model.addAttribute("customerId", customerId);
//        return "cartDetail"; // 返回對應的 HTML 頁面
//    }
    // POST 請求：儲存購物車資料
//    @PostMapping("/checkoutlist/saveItems")
//    @ResponseBody
//    public String saveCartItems(@RequestBody List<Cart> cartItems) {
//        // 儲存購物車資料的邏輯
//        cartService.saveCartItems(cartItems);
//        return "Cart items saved";
//    }
    // POST 請求：獲取商品資料
    @PostMapping("/checkoutlist/findByproductIds")
    @ResponseBody
    public List<Product> findProductsByIds(@RequestBody List<Integer> productIds) {
        return cartService.getProductsByIds(productIds);
    }
    // POST 請求：獲取商店資料
    @PostMapping("/checkoutlist/findByStoreId")
    @ResponseBody
    public Store findstoreById(@RequestBody Integer storeId) {
        return cartService.getStoreByid(storeId);
    }
}
