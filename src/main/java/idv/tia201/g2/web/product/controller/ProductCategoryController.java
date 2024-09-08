package idv.tia201.g2.web.product.controller;


import idv.tia201.g2.web.product.service.ProductCategoryService;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@RestController
@RequestMapping("categories")
public class ProductCategoryController {

   @Autowired
   private ProductCategoryService productCategoryService;
    //處理：允許添加、刪除或更新商品分類，以及顯示某一分類下的所有商品。
//獲取全部的category http://localhost:8080/categories/info
   @GetMapping("info")
   public ResponseEntity<List<ProductCategory>> getAllCategories() {
      List<ProductCategory> categories = productCategoryService.getAllCategories();
      return ResponseEntity.ok(categories);
   }

   @PostMapping("add")
   public ResponseEntity<ProductCategory> addCategory(@RequestBody ProductCategory productCategory) {
      productCategoryService.addCategory(productCategory);
      return ResponseEntity.ok(productCategory);
   }


   //http://localhost:8080/categories/delete/3
   @DeleteMapping("delete/{id}")
   public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer categoryId) {
      productCategoryService.removeCategory(categoryId); // 刪除指定 ID 的分類
      return ResponseEntity.noContent().build(); //HTTP 204 No Content 成功回應碼表示請求已成功，但用戶端不需離開當前頁面
   }


}
