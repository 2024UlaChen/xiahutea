package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.service.ProductCategoryService;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

// 查全部的分類
    @Override
    public List<ProductCategory> getAllCategories() {

        return productCategoryDao.findAll();
    }

    @Override
    public ProductCategory getProductCategoryById(Integer id) {
        return null;
    }


    @Override
    public void update(ProductCategory productCategory) {
        // 檢查待更新的分類是否存在
        if (productCategory.getCategoryId() == null || !productCategoryDao.existsById(productCategory.getCategoryId())) {
            throw new IllegalArgumentException("分類ID不存在");}

        if (productCategory.getCategoryName() != null && !productCategory.getCategoryName().isEmpty()) {
            // 查找已有的分類排序
            ProductCategory existingCategory = productCategoryDao.findByCategorySort(productCategory.getCategorySort());

            // 檢查分類排序是否已存在，檢查的分類排序是否屬於當前正在更新的分類
            if (existingCategory == null || existingCategory.getCategoryId().equals(productCategory.getCategoryId())) {
                // 執行更新操作
                productCategoryDao.save(productCategory);
            } else {
                throw new IllegalArgumentException("分類排序已重複");
            }
        } else {
            throw new IllegalArgumentException("分類名稱不為空");
        }



    }

    @Override
    public void addCategory(ProductCategory productCategory) {
        if (productCategory.getCategoryName() != null && !productCategory.getCategoryName().isEmpty()) {
            if (productCategoryDao.findByCategorySort(productCategory.getCategorySort()) == null) {
                productCategoryDao.save(productCategory);
            } else {
                throw new IllegalArgumentException("分類排序已重複");
            }
        } else {
            throw new IllegalArgumentException("分類名稱不為空");
        }


    }
        @Override
        public boolean removeCategory (Integer categoryId){
         //要check是否存在，存在就刪掉，不存在false
            if (productCategoryDao.existsById(categoryId)) {
                productCategoryDao.deleteById(categoryId);
                return true; // 表示删除成功
            } else {
              //
                return false; // 表示找不到紀錄
            }

        }
    }
