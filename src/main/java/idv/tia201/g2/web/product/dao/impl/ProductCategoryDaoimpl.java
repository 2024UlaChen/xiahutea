package idv.tia201.g2.web.product.dao.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;
import idv.tia201.g2.web.product.vo.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCategoryDaoimpl {
    //連線
    @PersistenceContext
    private EntityManager em;
    
//    @Override
//    public ProductCategory findById(Integer id) {
//
//        return null;
//    }
//
//    @Override
//    public int insert(ProductCategory productCategory) {
//
//        return 1;
//    }
//
//    @Override
//    public int update(ProductCategory productCategory) {
//        return 1;
//    }
//
//
//    @Override
//    public int deleteCategoryById(Integer id) {
//
//    return 1;
//    }
}
