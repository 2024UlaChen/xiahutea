package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductDao {
    List<Product> getAllproducts();
    int insert(Product product);
    int updateProduct(Product product);
    int deleteById(Integer id);
    Product getProductById(Integer id);


//來根據產品的 id 從資料庫中查詢並返回對應的 Product 實體。
}
