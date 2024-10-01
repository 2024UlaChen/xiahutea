package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;

import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
import idv.tia201.g2.web.product.service.ProductCategoryService;

import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.ProductCategory;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ProductCategory existingCategory = productCategoryDao.findByCategoryId(categoryId);

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



    @Override
    public String saveCategory(ProductCategoryDTO categoryDTO, Integer userId,Integer userTypeId) {
        // 进行输入校验，例如检查必填字段是否为空
        if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isEmpty()) {
            return "分类名称不能为空";
        }



        ProductCategory category = new ProductCategory();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategorySort(categoryDTO.getCategorySort());
        category.setCategoryStatus(categoryDTO.getCategoryStatus());
        category.setProductStoreId(userId);

        // 保存分类
        productCategoryDao.save(category);

        return "分类添加成功";
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


    @Override
    public boolean checkStoreOwnership(Integer userId,Integer storeId) {
        // 调用 StoreDAO 检查该用户是否为 storeId 对应店铺的拥有者
        Store store =storeDao.findByStoreId(storeId);
        return store != null && store.getStoreId().equals(userId);
    }

    @Override
    public Page<ProductCategory> getProductCategoryByStoreId(Integer productStoreId, Pageable pageable) {
         return productCategoryDao.findByProductStoreId(productStoreId, pageable);
    }


}

