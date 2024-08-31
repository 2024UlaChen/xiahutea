package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductCategoryDao;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.service.ProductCategoryService;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductCategoryimpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getAllProducts() {

        return productCategoryDao.findAll();
    }

    @Override
    public ProductCategory getProductCategoryById(Integer id) {
        return null;
    }

    @Override
    public boolean insertAndUpdateProduct(ProductCategory productCategory) {
        try {
            productCategoryDao.save(productCategory);
            return true;
        } catch (Exception exp) {
            return false;
        }
    }



    @Override
    public boolean deleteCategory(Integer id) {

        return false;
    }
}
