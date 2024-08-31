package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao  {

//    @Query("SELECT p FROM Product p WHERE p.Id= :Id AND p.productName=: productName")
//    List<Product> findByIdAndProductName(@Param("Id") Integer id, @Param("productName") String productName);

     List<Product> findByProductName(String productName);
//    getAllProducts 方法通常會查詢數據庫或其他數據源，並將所有 Product 實體對象以列表的形式返回。
//    int insert(Product product);
//    int updateProduct(Product product);
//    int deleteById(Integer id);
//    Product getProductById(Integer id);
    //來根據產品的 id 從資料庫中查詢並返回對應的 Product 實體。

//    List<Product> findByCategoryAndName(Long categoryId, String name);
//@Query("FROM Product p WHERE p.productCategoryId = :categoryid AND p.productName = :productname")
//List<Product> findByCategoryAndProductName(@Param("category") String category, @Param("name") String name);
}
