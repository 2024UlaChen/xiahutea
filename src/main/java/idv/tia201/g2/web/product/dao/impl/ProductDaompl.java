package idv.tia201.g2.web.product.dao.impl;

import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.vo.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ProductDaompl implements ProductDao {
    @Override
    public List<Product> findByProductName(String productName) {
        return null;
    }
    //    @Override
//    public List<Product> findByProductName(String productName) {
//        return null;
//    }
//
//    @Override
//    public List<Product> findByCategoryAndProductName(String category, String name) {
//        return null;
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//
//
//

//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public List<Product> getAllproducts() {
//
//        return null;
//    }
//
//    @Override
//    public int insert(Product product) {
//
//        return 1;
//    }
//
//    @Override
//    public int updateProduct(Product product) {
//
//        return 1;
//    }
//
//    @Override
//    public int deleteById(Integer id) {
//        return 1;
//    }
//
//    @Override
//    public Product getProductById(Integer id) {
//        return null;
//    }
//
//    @Override
//    public List<Product> findByCategoryAndName(Long categoryId, String name) {
//        return null;
//    }


}
