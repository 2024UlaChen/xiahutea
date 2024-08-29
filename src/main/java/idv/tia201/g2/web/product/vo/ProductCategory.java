package idv.tia201.g2.web.product.vo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_category", schema = "xiahu_db")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id; // 商品分類流水號
    @Column (name = "product_store_id")
    private Integer productStoreId;    // 店家編號
    @Column(name = "product_category")
    private String productCategory;    // 商品分類名稱
    @Column(name = "product_sort")
    private Integer productsort;

}

