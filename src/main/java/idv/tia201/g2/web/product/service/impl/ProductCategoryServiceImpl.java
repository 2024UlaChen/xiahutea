package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryRepository;

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
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private ProductService productService;

    // 查全部的分類
    @Override
    public List<ProductCategory> getAllCategories() {

        return productCategoryRepository.findAllByOrderByCategorySortAsc();
    }

    @Override
    public ProductCategory getProductCategoryById(Integer categoryId) {

        return productCategoryRepository.findByCategoryId(categoryId);
    }


    public ProductCategory update(Integer categoryId, ProductCategoryDTO updatedCategoryDTO, Integer userTypeId, Integer userId) {
        // 根据 categoryId 查找现有分类
        ProductCategory existingCategory = productCategoryRepository.findByCategoryId(categoryId);

        // 如果用戶是商家，驗證該分類是否屬於該商家
        if (userTypeId == 1 && !existingCategory.getProductStoreId().equals(userId)) {
           return null;
        }

        List<ProductCategory> duplicateCategories = productCategoryRepository.findByCategoryNameAndProductStoreId(updatedCategoryDTO.getCategoryName(), userId);

// 檢查是否存在不同 ID 的重複分類
        if (!duplicateCategories.isEmpty()) {
            for (ProductCategory category : duplicateCategories) {
                // 如果找到的分類 ID 與當前更新的分類不同，則表示分類名稱重複
                if (!category.getCategoryId().equals(categoryId)) {
                    return null;
                }
            }
        }

        if (existingCategory != null) {
            // 更新分类的字段
            existingCategory.setCategoryName(updatedCategoryDTO.getCategoryName());
            existingCategory.setCategorySort(updatedCategoryDTO.getCategorySort());
            existingCategory.setCategoryStatus(updatedCategoryDTO.getCategoryStatus());

            // 保存更新后的分类
            return productCategoryRepository.save(existingCategory);
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
        productCategoryRepository.save(category);

        return "分类添加成功";
    }


    @Override
    public boolean deleteCategory(Integer categoryId) {
        productCategoryRepository.deleteById(categoryId);

        return true;
    }

    @Override
    public List<ProductCategory> getCategoriesByName(String categoryName) {
        return productCategoryRepository.findByCategoryNameContaining(categoryName);
    }

    @Override
    public Page<ProductCategoryDTO> getCategories(Pageable pageable) {

        return productCategoryRepository.findProductCategoryDTO(pageable);
    }


    @Override
    public boolean checkStoreOwnership(Integer userId,Integer storeId) {
        // 调用 StoreDAO 检查该用户是否为 storeId 对应店铺的拥有者
        Store store =storeDao.findByStoreId(storeId);
        return store != null && store.getStoreId().equals(userId);
    }

    @Override
    public Page<ProductCategoryDTO> getProductCategoryByStoreId(Integer productStoreId, Pageable pageable) {
         return productCategoryRepository.findProductCategoryDTOByStoreId(productStoreId, pageable);
    }

    @Override
    public List<ProductCategory> getCategoriesByNameAndStoreId(String categoryName, Integer storeId) {
        return productCategoryRepository.findByCategoryNameAndProductStoreId(categoryName, storeId);
    }

    @Override
    public List<ProductCategory> getProductCategoryByStoreId(Integer storeId) {
        return productCategoryRepository.findByProductStoreIdOrderByCategorySortAsc(storeId);
    }

    @Override
    public List<ProductCategory> getCategoriesByStore(Integer storeId) {
        return productCategoryRepository.findByProductStoreId(storeId);
    }

    ;


}

