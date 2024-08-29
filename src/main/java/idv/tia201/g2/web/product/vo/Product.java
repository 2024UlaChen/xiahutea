package idv.tia201.g2.web.product.vo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product", schema = "xiahu_db")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "product_name")
    private String productName;
    @Column(name="product_price")
    private Integer productPrice;
    @Column(name="product_status")
    private boolean productStatus;
    private Integer productStoreId;
    @Column(name="product_picture")
    private byte[] productPicture;
    @Column(name="product_category_id")
    private Integer productCategoryId;
    @Column(name="normal_ice")
    private boolean normalIce;
    @Column(name="less_ice")
    private boolean lessIce;
    @Column(name="ice_free")
    private boolean iceFree;
    @Column(name="light_ice")
    private boolean lightIce;
    @Column(name="room_Temperature")
    private boolean roomTemperature;
    private boolean hot;
    @Column(name="full_sugar")
    private boolean fullSugar;
    @Column(name="less_sugar")
    private boolean lessSugar;
    @Column(name="half_sugar")
    private boolean halfSugar;
    @Column(name="quarter_sugar")
    private boolean quarterSugar;
    @Column(name="no_sugar")
    private boolean noSugar;
    private boolean pearl;
    private boolean pudding;
    @Column(name = "coconut_jelly")
    private boolean coconutJelly;
    private boolean taro;
    @Column(name = "herbal_jelly")
    private boolean herbalJelly;
    private String size;




}


//@Entity
//@Table(name = "categories")
//public class Category {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @OneToMany(mappedBy = "category")
//    private List<Product> products;
//
//    // Getters and Setters
//}
//
//@Entity
//@Table(name = "products")
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;
//
//    // Getters and Setters
//}