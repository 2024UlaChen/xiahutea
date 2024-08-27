package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllProducts();
    Product getProductCategoryById(Integer id);
    boolean addCategory(ProductCategory productcategory);
    boolean updateCategory(ProductCategory productcategory);
    boolean deleteCategory(Integer id);

}
