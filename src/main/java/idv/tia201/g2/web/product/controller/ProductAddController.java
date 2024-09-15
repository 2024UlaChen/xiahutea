package idv.tia201.g2.web.product.controller;



import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.List;


/*
 * 商品資料相關 Controller
 * 商品資料表單新增
 */
@RestController
@RequestMapping("/products")
public class ProductAddController {

    @Autowired
    private ProductService productService;
//查詢分類id以及商品名稱
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam Integer categoryId, @RequestParam String productName) {
        return productService.searchProducts(categoryId, productName);
    }
    // 查找所有產品
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 根據產品名稱查找產品
    @GetMapping("/searchByName")
    public List<Product> getProductsByProductName(@RequestParam String productName) {
        return productService.getProductsByProductName(productName);
    }

    // 新增產品
    @PostMapping("/add/{storeId}")
    public Product addProduct(@PathVariable Integer storeId, @RequestBody Product product) {
        return productService.addProduct(storeId, product);
    }

    // 編輯產品
    @PutMapping("/edit/{storeId}/{productId}")
    public Product editProduct(
            @PathVariable Integer storeId,
            @PathVariable Integer productId,
            @RequestBody Product updatedProduct) {
        return productService.editProduct(storeId, productId, updatedProduct);
    }

    // 刪除產品
    @DeleteMapping("/delete/{productId}")
    public boolean deleteProduct(@PathVariable Integer productId) {

        return productService.deleteProduct(productId);
    }




//    @PostMapping("addUpdateProduct")
//    public boolean addUpdateProduct(@RequestBody Product product) {
////        boolean flagAddUpdateProduct = false;
////        if (product.getProductId() != null){
////            flagAddUpdateProduct = productService.addUpdateProduct(product);
////        }
////        // 返 flagAddUpdateProduct 回前端
////        return flagAddUpdateProduct;
////    }
//
//
//

/**
 * 查詢 商品資料
 * @param product
 * @return
 */
//@PostMapping("/product/queryProduct")
//public String queryProduct(@RequestBody Product product) {
//
//
//
//
//    List<Product> productsList = new ArrayList<>();
//    if (product.getId() != null){
//      productService.insertAndUpdateProduct(product);
//    }
//    // 在这里处理接收到的产品数据
//    System.out.println("Received product: " + product.getProductName() + ", Price: " + product.getProductPrice());
//
//    String productListJSON;
//    ObjectMapper objectMapper = new ObjectMapper();
//    try {
//        //
//        productListJSON = objectMapper.writeValueAsString(productsList);
//    } catch (JsonProcessingException e) {
//        e.printStackTrace();
//        return null;
//    }
//    // 返 productListJSON 回前端
//    return productListJSON;
//}
//
}