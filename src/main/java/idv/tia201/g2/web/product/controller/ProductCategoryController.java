package idv.tia201.g2.web.product.controller;


import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
import idv.tia201.g2.web.product.service.ProductCategoryService;
import idv.tia201.g2.web.product.vo.ProductCategory;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

   @Autowired
   private ProductCategoryService productCategoryService;

   // 菜單店鋪 ID 獲取所有分類
   @GetMapping("/store/{storeId}")
   public ResponseEntity<List<ProductCategory>> getCategoriesForStore(@PathVariable Integer storeId) {
      List<ProductCategory> categories = productCategoryService.getCategoriesByStore(storeId);
      return ResponseEntity.ok(categories);
   }


   // 获取所有分类
   @GetMapping("/all")
   public List<ProductCategory> getAllCategories(HttpSession session) {
      TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");
      Integer userTypeId = totalUserDTO.getUserTypeId(); // 1: 商家, 3: 管理員
      Integer userId = totalUserDTO.getUserId(); // 商家的ID
      List<ProductCategory> categories = new ArrayList<>(); // Initialize the categories list

      // 根據用戶角色篩選產品分類
      if (userTypeId == 3) {
         // 管理員，返回所有店家產品分類資料
         categories = productCategoryService.getAllCategories();
      } else if (userTypeId == 1 || userTypeId== 0) {
         // 商家，過濾返回該商家的產品分類
         categories = productCategoryService.getProductCategoryByStoreId(userId);
      } else {
         // Optionally handle unexpected user types, e.g., log this event
         categories = new ArrayList<>(); // Return an empty list for unrecognized user types
      }

      return categories;
   };



   @GetMapping("/findByName")
   public List<ProductCategory> getCategoriesByName(@RequestParam String categoryName, HttpSession session) {
      TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");


      Integer userTypeId = totalUserDTO.getUserTypeId(); // 1: 商家, 3: 管理员
      Integer userId = totalUserDTO.getUserId(); // storeId 或 adminId

      List<ProductCategory> categories;

      if (userTypeId == 1) { // 商家
         // 店家只能查詢自己的分类

      categories=productCategoryService.getCategoriesByNameAndStoreId(categoryName, userId);
      } else { // 管理员
         // 管理员可以查詢所有分类
         categories = productCategoryService.getCategoriesByName(categoryName);
      }

      return categories; // 返回找到的分类
   }



   // 根据ID获取分类
   @GetMapping("/{categoryId}")
   public ProductCategory getCategoryById(@PathVariable Integer categoryId) {
      ProductCategory category = productCategoryService.getProductCategoryById(categoryId);
      return category;
   }

   // 添加新分类
   @PostMapping("/save")
   public ResponseEntity<Core> saveCategory(@RequestBody ProductCategoryDTO categoryDTO, HttpSession session) {
      Core core = new Core();
      core.setSuccessful(false);
      TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");

      // 确保 session 中的用户信息存在
      if (totalUserDTO == null) {
         core.setMessage("用户未登入");
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
      }

      Integer userTypeId = totalUserDTO.getUserTypeId(); // 1: 商家, 3: 管理员
      Integer userId = totalUserDTO.getUserId(); // storeId 或 adminId

      Core save = productCategoryService.saveCategory(categoryDTO, userId, userTypeId);

      return ResponseEntity.ok(save);
   }


   @PutMapping("/update/{categoryId}")
   public ResponseEntity<Core> updateCategory(@PathVariable Integer categoryId, @RequestBody ProductCategoryDTO updatedProductCategoryDTO,HttpSession session) {
      Core core = new Core();
      core.setSuccessful(false);
      core.setMessage("失敗");

      TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");
      if (totalUserDTO == null) {
         core.setMessage("未授權的用戶");
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(core);
      }
      Integer userTypeId = totalUserDTO.getUserTypeId(); // 1: 商家, 3: 管理员
      Integer userId = totalUserDTO.getUserId(); // storeId 或 adminId


      Core update = productCategoryService.update(categoryId, updatedProductCategoryDTO, userTypeId, userId);

      return ResponseEntity.ok(update);
   }

   // 删除分类
   @DeleteMapping("/delete/{categoryId}")
   public boolean deleteCategory(@PathVariable Integer categoryId) {
      return productCategoryService.deleteCategory(categoryId);
   }

   @GetMapping()
   public ResponseEntity<Page<ProductCategoryDTO>> getCategoriesPaginated(
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size,
           @RequestParam(required = false) String storeName,
           @RequestParam(required = false) String categoryName,
           HttpSession session) {


      // 從 session 中獲取 totalUserDTO
      TotalUserDTO totalUserDTO = (TotalUserDTO) session.getAttribute("totalUserDTO");
      // 如果 session 中沒有 totalUserDTO，返回 403 未授權
      if (totalUserDTO == null) {
         return new ResponseEntity<>(HttpStatus.FORBIDDEN);
      }

      Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("productStoreId"), Sort.Order.desc("productCategoryStatus"), Sort.Order.asc("categorySort")));
      Page<ProductCategoryDTO> categories;

      ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
      productCategoryDTO.setCategoryName(categoryName);

      // 1: 商家, 3: 管理員
      //店家只能搜尋自己的分類
      if (totalUserDTO.getUserTypeId() == 1) {
         // 商家，過濾返回該商家的產品分類
         productCategoryDTO.setProductStoreId(totalUserDTO.getUserId());
         productCategoryDTO.setStoreName((String) totalUserDTO.getData());
      } else if (totalUserDTO.getUserTypeId() == 3) {
         productCategoryDTO.setStoreName(storeName);
      } else {
         // 非商家或管理員角色，返回 403 未授權
         return new ResponseEntity<>(HttpStatus.FORBIDDEN);
      }

      categories = productCategoryService.serchCategories(productCategoryDTO, pageable);
      // 返回分頁結果
      return new ResponseEntity<>(categories, HttpStatus.OK);
   }


}