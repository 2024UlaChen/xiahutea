package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();
    ProductCategory getProductCategoryById(Integer categoryId);
   public ProductCategory update(Integer productStoreId,ProductCategory productCategory);
    public ProductCategory addproductCategory(ProductCategory productCategory);
    public boolean deleteCategory(Integer categoryId);
   public boolean deleteBatch(List<Integer> ids);
//   批量刪除

}
