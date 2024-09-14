package idv.tia201.g2.web.product.controller;


import idv.tia201.g2.web.product.service.ProductCategoryService;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

   @Autowired
   private ProductCategoryService productCategoryService;

   // 获取所有分类
   @GetMapping("/all")
   public List<ProductCategory> getAllCategories() {
     return  productCategoryService.getAllCategories();

// 並使用 Category 的 id 和 categoryName 創建一個新的 Category 物件。在新增商品可以選商品分類的選項
   }

   // 根据ID获取分类
   @GetMapping("/{id}")
   public ProductCategory getCategoryById(@PathVariable Integer id) {
      ProductCategory category = productCategoryService.getProductCategoryById(id);
      return category;
   }

   // 添加新分类
   @PostMapping("/add")
   public ProductCategory addCategory(@RequestParam Integer storeId, @RequestBody ProductCategory productCategory) {
      ProductCategory addedCategory = productCategoryService.addproductCategory(storeId, productCategory);
      return addedCategory;
   }

   // 更新分类
   @PutMapping("/update")
   public ProductCategory updateCategory(@RequestParam Integer storeId, @RequestBody ProductCategory productCategory) {
      ProductCategory updatedCategory = productCategoryService.update(storeId, productCategory);
      return updatedCategory;
   }

   // 删除分类
   @DeleteMapping("/delete/{id}")
   public boolean deleteCategory(@PathVariable Integer id) {
      return productCategoryService.deleteCategory(id);
   }

//  批量刪除分類

   @PostMapping("/delete-batch")
   public String deleteBatch(@RequestBody Map<String, List<Integer>> requestBody) {
      List<Integer> ids = requestBody.get("ids");

      if (ids == null || ids.isEmpty()) {
         return "請提供要刪除的項目ID";
      }

      boolean success = productCategoryService.deleteBatch(ids);
      return success ? "選中的項目已刪除" : "刪除失敗";
   }
}