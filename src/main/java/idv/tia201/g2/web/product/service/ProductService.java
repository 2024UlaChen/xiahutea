package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.vo.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    List<Product> getProductsByProductName(String productName);
   void addProduct(Product product);
   boolean removeProduct(Integer productId);

}

