package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;

import idv.tia201.g2.web.product.service.ProductCategoryService;

import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.ProductCategory;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private ProductService productService;

    // 查全部的分類
    @Override
    public List<ProductCategory> getAllCategories() {

        return productCategoryDao.findAll();
    }

    @Override
    public ProductCategory getProductCategoryById(Integer categoryId) {

        return productCategoryDao.findById(categoryId).orElse(null);
    }


    @Override
    public ProductCategory update(Integer categoryId, ProductCategory updatedProductCategory) {
        // 1. 檢查分類名稱是否有效
        if (updatedProductCategory.getCategoryName() == null || updatedProductCategory.getCategoryName().isEmpty()) {
            // 如果分類名稱無效，返回null
            return null;
        }

        // 2. 檢查排序號碼是否已被其他分類使用
        boolean sortOrderExists = productCategoryDao.existsByCategorySortAndCategoryIdNot(
                updatedProductCategory.getCategorySort(), categoryId);

        if (sortOrderExists) {
            // 如果排序號碼重複，返回null
            return null;
        }

        // 3. 查找現有的分類資料
        ProductCategory existingCategory = productCategoryDao.findByCategoryId(categoryId);
        if (existingCategory == null) {
            // 如果分類不存在，返回null
            return null;
        }

        // 4. 更新分類名稱和排序編號
        existingCategory.setCategoryName(updatedProductCategory.getCategoryName());
        existingCategory.setCategorySort(updatedProductCategory.getCategorySort());

        // 5. 保存更新後的分類
        productCategoryDao.save(existingCategory);

        // 6. 返回已更新的分類
        return existingCategory;
    }

    public ProductCategory addproductCategory(ProductCategory productCategory) {

        // 查詢是否有這個店家
//        Store store = storeDao.findById(productCategory.getProductStoreId()).orElse(null);
//        if (store == null) {
//            // 如果找不到店家，返回null
//            return null;
//        }
//
if (productCategory.getCategoryName() == null || productCategory.getCategoryName().isEmpty()) {

            return null;
        }
//
//        // 檢查分類排序是否在同一個店家內已存在
//        if (productCategoryDao.findByCategorySortAndProductStoreId(productCategory.getCategorySort(), store.getStoreId()) != null) {
//            // 如果分類排序已經存在，返回null，表示分類排序重複
//            return null;
//        }
        productCategoryDao.save(productCategory);
        // 返回保存的產品分類
        return productCategory;
    }

    @Override
    public boolean deleteCategory(Integer categoryId) {
        return false;
    }

    @Override
    public List<ProductCategory> getCategoriesByName(String categoryName) {
        return productCategoryDao.findByCategoryNameContaining(categoryName);
    }

    @Override
    public Page<ProductCategory> getCategories(Pageable pageable) {
        return productCategoryDao.findAll(pageable);
    }

//    @Override
//    public boolean deleteCategory(Integer categoryId) {
//
//        productService.deleteProductsByCategoryId(categoryId);
//        //要check是否存在，存在就刪掉，不存在false
//
//       if (productCategoryDao.existsById(categoryId)) {
//            productCategoryDao.deleteById(categoryId);
//            return true; // 表示删除成功
//        } else {
//
//         return false; // 表示找不到紀錄
//       }
//
//    }
//   批量刪除


}