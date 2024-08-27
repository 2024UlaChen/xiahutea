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
    private ProductCategoryDao dao;

    @Override
    public List<ProductCategory> getAllProducts() {
        return null;
    }

    @Override
    public Product getProductCategoryById(Integer id) {
        return null;
    }

    @Override
    public boolean addCategory(ProductCategory productcategory) {
        return false;
    }

    @Override
    public boolean updateCategory(ProductCategory productcategory) {
        return false;
    }

    @Override
    public boolean deleteCategory(Integer id) {
        return false;
    }
}
