package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.vo.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(Integer id);
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(Integer id);
    Product onsale(Integer id);
    Product offsale(Integer id);
}

