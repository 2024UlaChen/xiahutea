package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {
    List<ProductCategory> findAll();
    ProductCategory findById(Integer id);
    void save(ProductCategory productCategory);
    int deleteCategoryById(Integer id);

}
