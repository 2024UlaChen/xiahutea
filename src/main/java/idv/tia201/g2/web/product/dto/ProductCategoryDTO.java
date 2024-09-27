package idv.tia201.g2.web.product.dto;


import lombok.Data;

@Data
public class ProductCategoryDTO {

    private String categoryName;
    private Integer categorySort;
    private Boolean categoryStatus;  // 使用 boolean 來表示啟用或停用
    private Integer productStoreId;

}
