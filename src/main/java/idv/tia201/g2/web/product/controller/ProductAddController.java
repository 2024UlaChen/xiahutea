package idv.tia201.g2.web.product.controller;



import idv.tia201.g2.web.advertise.service.AdsService;
import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;

import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import idv.tia201.g2.web.product.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;


import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.Base64;
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


    @Autowired
    private AdsService adsservice;



    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam("storeId") Integer storeId) {
        return productService.findProductsByStoreId(storeId);
    }


    @GetMapping("/store/{storeId}") // 在 storeId 前面加上 /
    public List<Product> getProductsByStoreId(@PathVariable Integer storeId) {
        return productService.findProductsByStoreId(storeId);
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



    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam Integer productCategoryId,
            @RequestParam String productName,
            HttpSession session
    ) {
        try {
            // 從 session 中獲取 TotalUserDTO
            TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");
            Integer userTypeId = totalUserDTO.getUserTypeId(); // 1 : 商家, 3 : 管理員
            Integer userId = totalUserDTO.getUserId(); // storeId 或 adminId

            List<Product> products;

            // 如果是管理員
            if (userTypeId == 3) {
                products = productService.searchProducts(productCategoryId, productName);
            }
            // 如果是商家
            else if (userTypeId == 1) {
                products = productService.searchProducts(userId, productCategoryId, productName);
            } else {
                // 如果用戶類型無法識別，返回空列表或適當錯誤
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            // 返回200 OK狀態及查詢結果
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            // 返回500 Internal Server Error狀態及錯誤信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Integer productId) {
        try {
            ProductDTO productDTO = productService.getProductById(productId);
            if (productDTO != null) {
                return ResponseEntity.ok(productDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO,HttpSession session) throws IOException {
        // 获取店铺ID
        // 调用 Service 层进行处理
        TotalUserDTO totalUserDTO = (TotalUserDTO)session.getAttribute("totalUserDTO");
        Integer userTypeId = totalUserDTO.getUserTypeId(); // 1 : 商家 、 3 : 管理員
        Integer userId = totalUserDTO.getUserId();// storeId 或 adminId

        try {
            // 将 Base64 数据解码为字节数组
            byte[] fileBytes = Base64.getDecoder().decode(productDTO.getProductPicture());
            // 將圖片資料byte轉換為 Blob 物件
            //Blob blob = new SerialBlob(fileBytes);
            productService.addProduct(productDTO, userTypeId, userId, fileBytes);
            return ResponseEntity.ok("商品新增成功!");
        } catch (Exception exp){
            exp.getStackTrace();
            System.out.println(exp.getMessage());
        };

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("商品新增失败!");
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable int productId,@RequestBody ProductDTO productDTO ,HttpSession session) {
        TotalUserDTO totalUserDTO = (TotalUserDTO)session.getAttribute("totalUserDTO");
        Integer userTypeId = totalUserDTO.getUserTypeId(); // 1 : 商家 、 3 : 管理員
        Integer userId = totalUserDTO.getUserId();// storeId 或 adminId
        try {
            // 将 Base64 数据解码为字节数组
            byte[] fileBytes = Base64.getDecoder().decode(productDTO.getProductPicture());
            boolean success = productService.updateProduct(productId, productDTO,userTypeId,userId, fileBytes);

            if (success) {
                return ResponseEntity.ok("商品更新成功!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("商品更新失败!");
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器错误: " + e.getMessage());
        }
    }
    // 刪除產品
    @DeleteMapping("/delete/{productId}")
    public boolean deleteProduct(@PathVariable Integer productId) {

        return productService.deleteProduct(productId);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> getProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session) {

        // 從 session 中獲取 totalUserDTO
        TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");

        // 如果 session 中沒有 totalUserDTO，返回 403 未授權
        if (totalUserDTO == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // 獲取用戶類型和用戶ID
        Integer userTypeId = totalUserDTO.getUserTypeId(); // 1: 商家, 3: 管理員
        Integer userId = totalUserDTO.getUserId(); // 商家ID

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;

        // 根據用戶角色篩選產品
        if (userTypeId == 3) {
            // 管理員，返回所有店家產品資料
            products = productService.getProducts(pageable);
        } else if (userTypeId == 1) {
            // 商家，過濾返回該商家的產品
            products = productService.getProductByStoreId(userId, pageable);
        } else {
            // 非商家或管理員角色，返回空結果或者拋出未授權
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // 返回分頁結果
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/byCategory")
    public List<ProductDTO> getProductsByCategory(@RequestParam Integer ProductCategoryId) {
        return productService.getProductsByCategory(ProductCategoryId);
    }

    @GetMapping("/byCategories")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @RequestParam Integer productCategoryId,
            HttpSession session) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");
        Integer storeId = totalUserDTO.getUserId(); // 這裡的 userId 對應商店 ID
        List<Product> products = productService.getProductsByCategoryAndStore(storeId, productCategoryId);
        return ResponseEntity.ok(products);
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("productPicture") MultipartFile file) {
        try {
            productService.saveImage(file);
            return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

// 根據產品ID獲取產品圖片
@GetMapping("/{id}/image")
public ResponseEntity<String> getProductImage(@PathVariable Integer id) {
    byte[] imageData = productService.getProductImage(id);
    if (imageData != null && imageData.length > 0) {
        // 將圖片數據轉換為Base64字符串
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        // 返回Base64字符串，並設置Content-Type為text/plain
        return ResponseEntity.ok().header("Content-Type", "text/plain").body(base64Image);
    } else {
        // 如果找不到圖片，返回404
        return ResponseEntity.notFound().build();
    }
    }

    @GetMapping("/advertise/image/{adsTotalUserid}")
    public ResponseEntity<String> getFirstImageByUserId(@PathVariable Long adsTotalUserid) {
        byte[] image = productService.getFirstImageByUserId(adsTotalUserid);

        if (image != null && image.length > 0) {
            // 將圖片數據轉換為Base64字符串
            String base64Image = Base64.getEncoder().encodeToString(image);
            // 返回Base64字符串，並設置Content-Type為text/plain
            return ResponseEntity.ok().header("Content-Type", "text/plain").body(base64Image);
        } else {
            // 如果找不到圖片，返回404
            return ResponseEntity.notFound().build();
        }
    }



}




