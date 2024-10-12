package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();
    ProductCategory getProductCategoryById(Integer categoryId);
    ProductCategory update(Integer categoryId, ProductCategoryDTO updatedCategoryDTO,Integer userTypeId, Integer userId);
    String saveCategory(ProductCategoryDTO categoryDTO, Integer userId,Integer userTypeId);
    boolean deleteCategory(Integer categoryId);
    List<ProductCategory> getCategoriesByName(String categoryName);
    Page<ProductCategoryDTO> getCategories(Pageable pageable);
    Page<ProductCategoryDTO> getProductCategoryByStoreId(Integer productStoreId, Pageable pageable);
    List<ProductCategory> getCategoriesByNameAndStoreId(String categoryName, Integer storeId);
    List<ProductCategory>getProductCategoryByStoreId(Integer productStoreId);
    List<ProductCategory> getCategoriesByStore(Integer storeId);

    Page<ProductCategoryDTO> serchCategories(ProductCategoryDTO productCategoryDTO, Pageable pageable);

}
