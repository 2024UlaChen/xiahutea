package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.dao.impl.ProductDaompl;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceimpl implements ProductService {
   @Autowired
    private ProductDao dao;

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product getProductById(Integer id) {
        return null;
    }

    @Override
    public boolean addProduct(Product product) {
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    @Override
    public boolean deleteProduct(Integer id) {
        return false;
    }

    @Override
    public Product onsale(Integer id) {
        return null;
    }

    @Override
    public Product offsale(Integer id) {
        return null;
    }
}
