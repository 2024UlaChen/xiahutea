package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();
    ProductCategory getProductCategoryById(Integer id);
   void update(ProductCategory productCategory);
    void addCategory(ProductCategory productCategory);
    boolean deleteCategory(Integer categoryId);

}
