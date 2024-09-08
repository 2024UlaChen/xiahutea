package idv.tia201.g2.web.product.service.impl;

import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.service.ProductService;
import idv.tia201.g2.web.product.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getAllProducts() {

        return productDao.findAll();
    }
//獲取產品名字
    public List<Product> getProductsByProductName(String productName) {
        return productDao.findByProductNameContaining(productName);

    }
//新增
    @Override
    public void addProduct(Product product) {

    }
//刪除
    @Override
    public boolean removeProduct(Integer productId) {
       if(productDao.existsById(productId)){
           productDao.deleteById(productId);
           return true;
        }else{
           return false;
        }
    }
}
