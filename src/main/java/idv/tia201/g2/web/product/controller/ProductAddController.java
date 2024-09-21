package idv.tia201.g2.web.product.controller;



import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import idv.tia201.g2.web.product.dto.ProductDTO;

import java.io.IOException;
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
    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductStatus(productDTO.isProductStatus());
        product.setProductStoreId(productDTO.getProductStoreId());
        product.setProductCategoryId(productDTO.getProductCategoryId());

        try {
            // 將圖片轉換為 byte[] 並存入 product
            byte[] productPicture = productDTO.getProductPictureFile().getBytes();
            product.setProductPicture(productPicture);
        } catch (IOException e) {
            return "Failed to process product image";
        }

        // 設置其他產品屬性
        product.setNormalIce(productDTO.isNormalIce());
        product.setLessIce(productDTO.isLessIce());
        product.setIceFree(productDTO.isIceFree());
        product.setLightIce(productDTO.isLightIce());
        product.setRoomTemperature(productDTO.isRoomTemperature());
        product.setHot(productDTO.isHot());
        product.setFullSugar(productDTO.isFullSugar());
        product.setLessSugar(productDTO.isLessSugar());
        product.setHalfSugar(productDTO.isHalfSugar());
        product.setQuarterSugar(productDTO.isQuarterSugar());
        product.setNoSugar(productDTO.isNoSugar());
        product.setPearl(productDTO.isPearl());
        product.setPudding(productDTO.isPudding());
        product.setCoconutJelly(productDTO.isCoconutJelly());
        product.setTaro(productDTO.isTaro());
        product.setHerbalJelly(productDTO.isHerbalJelly());
        product.setSize(productDTO.getSize());

        // 保存產品
        Product savedProduct = productService.addProduct(product);
        if (savedProduct != null) {
            return "Product added successfully";
        }
        return "Failed to add product";
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

    @GetMapping("/paginated")
    public Page<Product> getProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getProducts(pageable);
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