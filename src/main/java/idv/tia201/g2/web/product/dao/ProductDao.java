package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import idv.tia201.g2.web.store.vo.Store;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product,Integer>  {

    List<Product> findByProductNameContaining(String name);
//   用id去找分類底下的productm用來顯示productmenu
    List<Product> findByProductCategoryId(Integer categoryId);
 //用產品分類及商品名稱去做查詢，搜尋欄
    List<Product> findByProductCategoryIdAndProductNameContaining(Integer productCategoryId, String productName);
   //分頁處理
    Page<Product> findAll(Pageable pageable);
    List<Product> findByProductId(Integer ProductId);


}
