package idv.tia201.g2.web.product.vo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "product_category", schema = "xiahu_db")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id; // 商品分類編號

    private Integer productStoreId;    // 店家編號
    @Column(name = "product_category")
    private String productCategory;    // 商品分類名稱
    @Column(name = "product_sort")
    private Integer productsort;

@OneToMany(mappedBy = "productcategory")
    private List<Product> product;

}

