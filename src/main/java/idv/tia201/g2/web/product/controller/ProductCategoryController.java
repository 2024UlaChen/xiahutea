package idv.tia201.g2.web.product.controller;


import idv.tia201.g2.web.product.dao.ProductCategoryDao;
import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
import idv.tia201.g2.web.product.service.ProductCategoryService;

import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

   @Autowired
   private ProductCategoryService productCategoryService;
   @Autowired
   private ProductCategoryDao productCategoryDao;

   // 获取所有分类
   @GetMapping("/all")
   public List<ProductCategory> getAllCategories() {
     return  productCategoryService.getAllCategories();

// 並使用 Category 的 id 和 categoryName 創建一個新的 Category 物件。在新增商品可以選商品分類的選項
   }
//用名稱去查找資料
   @GetMapping("/findByName")
   public List<ProductCategory> getCategoriesByName(@RequestParam String categoryName) {
      List<ProductCategory> categories = productCategoryService.getCategoriesByName(categoryName);
      if (categories.isEmpty()) {
         // 返回空列表，不需要特別處理
         return Collections.emptyList();
      } else {
         return categories;
      }
   }


   // 根据ID获取分类
   @GetMapping("/{categoryId}")
   public ProductCategory getCategoryById(@PathVariable Integer categoryId) {
      ProductCategory category = productCategoryService.getProductCategoryById(categoryId);
      return category;
   }

   // 添加新分类
   @PostMapping("/save")
   public String saveCategory(@RequestBody ProductCategoryDTO categoryDTO) {


      productCategoryService.saveCategory(categoryDTO);

      return "加入成功";
   }

   @PutMapping("/update/{categoryId}")
   public ResponseEntity<String> updateCategory(@PathVariable Integer categoryId, @RequestBody ProductCategoryDTO updatedProductCategoryDTO) {

      ProductCategory updatedCategory = productCategoryService.update(categoryId, updatedProductCategoryDTO);

      if (updatedCategory != null) {
         return ResponseEntity.ok("分类修改成功！");
      } else {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("修改失败，可能分类排序不唯一或分类不存在。");
      }
   }

   // 删除分类
   @DeleteMapping("/delete/{categoryId}")
   public boolean deleteCategory(@PathVariable Integer categoryId) {
      return productCategoryService.deleteCategory(categoryId);
   }

   @GetMapping("/paginated")
   public Page<ProductCategory> getCategoriesPaginated(
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size) {
      Pageable pageable = PageRequest.of(page, size);
      return productCategoryService.getCategories(pageable);
   }


}