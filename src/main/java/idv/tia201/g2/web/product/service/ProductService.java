package idv.tia201.g2.web.product.service;

import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
import idv.tia201.g2.web.product.dto.ProductDTO;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.product.vo.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    List<Product> getProductsByProductName(String productName);
    public boolean addProduct(ProductDTO productDTO,Integer userTypeId,Integer userId) throws IOException;
    public boolean updateProduct(Integer productId,ProductDTO productDTO, Integer userTypeId, Integer userId);
    public boolean deleteProduct(Integer productId);
    ProductDTO getProductById(Integer productId);
    Page<Product> getProducts(Pageable pageable);
    List<Product> searchProducts(Integer productCategoryId, String productName);
    List<ProductCategoryDTO> getProductsByCategory();
    public List<ProductDTO> getProductsByCategory(Integer ProductCategoryId);

    void updateProductImage(Integer productId, byte[] imageBytes);
    Page<Product> getProductByStoreId(Integer productStoreId, Pageable pageable);
    public List<Product> searchProducts(Integer storeId, Integer productCategoryId, String productName);
    List<Product> getProductsByCategoryAndStore(Integer storeId, Integer productCategoryId);
    Product saveImage(MultipartFile file) throws IOException;

    List<Product> findProductsByStoreId(Integer storeId);
    public byte[] getProductImage(Integer productId);

}

