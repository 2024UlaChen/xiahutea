package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.dto.ProductDTO;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    List<Product> getProductsByProductName(String productName);
    public boolean addProduct(ProductDTO productDTO);
    public boolean updateProduct(ProductDTO productDTO);
    public boolean deleteProduct(Integer productId);
    List<Product> searchProducts(Integer categoryId, String productName);
    Page<Product> getProducts(Pageable pageable);

}

