package idv.tia201.g2.web.product.dao.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;
import idv.tia201.g2.web.product.vo.ProductCategory;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ProductCategoryDaoimpl implements ProductCategoryDao {

//連線
@PersistenceContext
    @Override
    public List<ProductCategory> findAll() {

        return null;
    }

    @Override
    public ProductCategory findById(Integer id) {
        return null;
    }

    @Override
    public void save(ProductCategory productCategory) {

    }

    @Override
    public int deleteCategoryById(Integer id) {

    return 1;
    }
}
