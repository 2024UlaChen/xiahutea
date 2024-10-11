package idv.tia201.g2.web.product.dao;

import idv.tia201.g2.web.product.dto.ProductCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryOperation {

    Page<ProductCategoryDTO> findProductCategoryDTOByStoreId(Integer storeId, Pageable pageable);

    Page<ProductCategoryDTO> findProductCategoryDTO(Pageable pageable);

    Page<ProductCategoryDTO> findProductCategoryDTOByStoreName(String storeName, Pageable pageable);

    Page<ProductCategoryDTO> findProductCategoryDTOByCategoryName(String categoryName, Pageable pageable);

    Page<ProductCategoryDTO> findProductCategoryDTOByCategoryNameAndStoreName(String categoryName, String storeName, Pageable pageable);

}
