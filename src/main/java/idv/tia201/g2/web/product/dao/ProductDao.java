package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import idv.tia201.g2.web.store.vo.Store;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product,Integer>  {

    List<Product> findByProductNameContaining(String name);
//   用id去找分類底下的product
    List<Product> findByProductCategoryId(Integer categoryId);
}
