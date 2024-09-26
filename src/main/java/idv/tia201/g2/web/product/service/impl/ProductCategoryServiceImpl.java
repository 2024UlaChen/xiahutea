package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;

import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
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


    public ProductCategory update(Integer categoryId, ProductCategoryDTO updatedCategoryDTO) {
        // 根据 categoryId 查找现有分类
        ProductCategory existingCategory = productCategoryDao.findById(categoryId).orElse(null);

        if (existingCategory != null) {
            // 更新分类的字段
            existingCategory.setCategoryName(updatedCategoryDTO.getCategoryName());
            existingCategory.setCategorySort(updatedCategoryDTO.getCategorySort());
            existingCategory.setCategoryStatus(updatedCategoryDTO.getCategoryStatus());

            // 保存更新后的分类
            return productCategoryDao.save(existingCategory);
        } else {
            // 分类不存在的情况
            return null;
        }
    }


    public void saveCategory(ProductCategoryDTO categoryDTO) {
        ProductCategory category = new ProductCategory();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategorySort(categoryDTO.getCategorySort());
        category.setCategoryStatus(categoryDTO.getCategoryStatus());  // 注意這裡的 isStatus() 用來取得 boolean 值


        productCategoryDao.save(category);
    }


    @Override
    public boolean deleteCategory(Integer categoryId) {
        productCategoryDao.deleteById(categoryId);

        return true;
    }

    @Override
    public List<ProductCategory> getCategoriesByName(String categoryName) {
        return productCategoryDao.findByCategoryNameContaining(categoryName);
    }

    @Override
    public Page<ProductCategory> getCategories(Pageable pageable) {
        return productCategoryDao.findAll(pageable);
    }


}