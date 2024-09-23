package idv.tia201.g2.web.product.controller;



import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;

import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.vo.Store;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import idv.tia201.g2.web.product.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;


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
    @Autowired
    private  StoreService storeService;

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


    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
        // 获取店铺ID


        // 调用 Service 层进行处理
        boolean success = productService.addProduct(productDTO);

        if (success) {
            return ResponseEntity.ok("商品新增成功!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("商品新增失败!");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
        try {
            // 调用 Service 层进行处理
            boolean success = productService.updateProduct(productDTO);

            if (success) {
                return ResponseEntity.ok("商品更新成功!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("商品更新失败!");
            }
        } catch (Exception e) {
            // 捕捉并记录异常，返回500错误
            // 使用日志记录异常

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器错误: " + e.getMessage());
        }
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
}




