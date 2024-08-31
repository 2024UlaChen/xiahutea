package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllProducts();
    ProductCategory getProductCategoryById(Integer id);
    boolean insertAndUpdateProduct(ProductCategory productCategory);
    boolean deleteCategory(Integer id);
}
