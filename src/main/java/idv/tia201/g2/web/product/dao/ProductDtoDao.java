package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductDtoDao {

    Page<ProductDTO> findProductDTOList(Pageable pageable);

    Page<ProductDTO> findProductDTOListByProductName(String productName, Pageable pageable);

    Page<ProductDTO> findProductDTOListByProductCategoryName(String productCategoryName, Pageable pageable);

    Page<ProductDTO> findProductDTOListByStoreName(String storeName, Pageable pageable);

    Page<ProductDTO> findProductDTOListByStoreNameAndProductCategoryName(String storeName, String productCategoryName, Pageable pageable);

    Page<ProductDTO> findProductDTOListByStoreNameAndProductName(String storeName, String productName, Pageable pageable);

    Page<ProductDTO> findProductDTOListByProductCategoryNameAndProductName(String productCategoryName, String productName, Pageable pageable);

    Page<ProductDTO> findProductDTOListByProductCategoryNameAndProductNameAndStoreName(String productCategoryName, String productName, String storeName, Pageable pageable);
}
