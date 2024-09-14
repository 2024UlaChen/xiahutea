package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.vo.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    List<Product> getProductsByProductName(String productName);
    public Product addProduct(Integer storeId, Product product);
    public Product editProduct(Integer storeId, Integer productId, Product updatedProduct);
    public boolean deleteProduct(Integer productId);
    List<Product> searchProducts(Integer categoryId, String productName);

}

