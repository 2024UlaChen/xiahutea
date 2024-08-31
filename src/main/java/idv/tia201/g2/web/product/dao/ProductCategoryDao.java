package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {

//    List<ProductCategory> findByProductCategory(String productCategory);
//    List<ProductCategory> findAll();
//    ProductCategory findById(Integer id);
//    int insert(ProductCategory productCategory);
//    int update(ProductCategory productCategory);
//    int deleteCategoryById(Integer id);

}
