package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceimpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getAllProducts() {

        return productDao.findAll();
    }

    public List<Product> getProductsByProductName(String productName) {
        return productDao.findByProductNameContaining(productName);

    }

    @Override
    public boolean insertAndUpdateProduct(Product product) {
        try {
            productDao.save(product);
            return true;
        } catch (Exception exp) {
            return false;
        }
    }

    @Override
    public boolean deleteProduct(Integer id) {
        try {
            Product product = new Product();
            product.setProductId(id);
            productDao.delete(product);
            return true;
        } catch (Exception exp) {
            return false;
        }
    }
}
