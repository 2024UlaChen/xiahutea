package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;

import idv.tia201.g2.web.product.service.ProductCategoryService;

import idv.tia201.g2.web.product.vo.ProductCategory;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private StoreDao storeDao;

    // 查全部的分類
    @Override
    public List<ProductCategory> getAllCategories() {

        return productCategoryDao.findAll();
    }

    @Override
    public ProductCategory getProductCategoryById(Integer categoryId) {
        return null;
    }


    @Override
    public ProductCategory update(Integer productStoreId, ProductCategory productCategory) {
        if (productCategory.getCategoryId() == null || !productCategoryDao.existsById(productCategory.getCategoryId())) {
            // 如果分類ID不存在，返回null
            return null;
        }

        // 確認該分類是否屬於該店家
        ProductCategory existingCategory = productCategoryDao.findById(productCategory.getCategoryId()).orElse(null);
        if (existingCategory == null || !existingCategory.getProductStoreId().equals(productStoreId)) {
            // 如果分類不屬於該店家，返回null
            return null;
        }

        // 檢查分類名稱是否有效
        if (productCategory.getCategoryName() == null || productCategory.getCategoryName().isEmpty()) {
            // 如果分類名稱無效，返回null
            return null;
        }

        // 查找已有的分類排序
        ProductCategory categoryWithSameSort = productCategoryDao.findByCategorySortAndProductStoreId(productCategory.getCategorySort(), productStoreId);

        // 檢查分類排序是否已存在，並確保檢查的分類排序不屬於其他分類
        if (categoryWithSameSort == null || categoryWithSameSort.getCategoryId().equals(productCategory.getCategoryId())) {
            // 如果排序可用，執行更新操作
            productCategoryDao.save(productCategory);
            return productCategory; // 返回更新後的分類
        } else {
            // 如果分類排序重複，返回null
            return null;
        }


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

        // 如果一切檢查都通過，則保存分類

        productCategoryDao.save(productCategory);
        // 返回保存的產品分類
        return productCategory;
    }

    @Override
    public boolean deleteCategory(Integer categoryId) {
        //要check是否存在，存在就刪掉，不存在false
        if (productCategoryDao.existsById(categoryId)) {
            productCategoryDao.deleteById(categoryId);
            return true; // 表示删除成功
        } else {
            //
            return false; // 表示找不到紀錄
        }

    }
//   批量刪除
    @Override
    public boolean deleteBatch(List<Integer> ids) {


        // 檢查是否所有ID都存在
        boolean allExist = ids.stream()
                .allMatch(id -> productCategoryDao.existsById(id));

        if (allExist) {
            // 如果所有ID都存在，則執行刪除
            productCategoryDao.deleteAllByCategoryIdIn(ids);
            return true; // 返回刪除成功
        } else {
            // 如果有ID不存在，返回刪除失敗
            return false;
        }
    }

}