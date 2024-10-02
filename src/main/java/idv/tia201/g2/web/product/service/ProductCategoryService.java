package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();
    ProductCategory getProductCategoryById(Integer categoryId);
    public ProductCategory update(Integer categoryId, ProductCategoryDTO updatedCategoryDTO);
    public String saveCategory(ProductCategoryDTO categoryDTO, Integer userId,Integer userTypeId);
    public boolean deleteCategory(Integer categoryId);
    List<ProductCategory> getCategoriesByName(String categoryName);
    Page<ProductCategory> getCategories(Pageable pageable);
    public boolean checkStoreOwnership(Integer userId, Integer storeId);
    Page<ProductCategory> getProductCategoryByStoreId(Integer productStoreId, Pageable pageable);



}
