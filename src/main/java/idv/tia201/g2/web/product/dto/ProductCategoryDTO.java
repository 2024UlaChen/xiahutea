package idv.tia201.g2.web.product.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryDTO {

    private String categoryName;
    private Integer categorySort;
    private Boolean categoryStatus;  // 使用 boolean 來表示啟用或停用
    private Integer productStoreId;
    private String storeName;
    private Integer categoryId;
//    private List<ProductDTO> products;

}
