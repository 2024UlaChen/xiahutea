package idv.tia201.g2.web.product.dao.impl;

import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaompl implements ProductDao {


    @Override
    public List<Product> getAllproducts() {

        return null;
    }

    @Override
    public int insert(Product product) {

        return 1;
    }

    @Override
    public int updateProduct(Product product) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public Product getProductById(Integer id) {
        return null;
    }
}
