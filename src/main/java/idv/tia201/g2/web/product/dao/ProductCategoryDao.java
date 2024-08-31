package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {
}
