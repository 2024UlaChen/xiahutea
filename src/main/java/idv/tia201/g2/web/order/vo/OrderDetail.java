package idv.tia201.g2.web.order.vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_detail", schema = "xiahu_db")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Integer orderDetailId; // 訂單明細編號

    @Column(name = "order_id")
    private Integer orderId; // 訂單編號
    @OneToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Orders order;

    @JoinColumn(name = "product_id")
    private Integer productId; // 商品編號

    @Column(name = "product_sugar")
    private String productSugar; // 甜度

    @Column(name = "product_temperature")
    private String productTemperature; // 溫度

    @Column(name = "product_add_materials")
    private String productAddMaterials; // 加料

    @Column(name = "product_quantity")
    private Integer productQuantity; // 商品數量

    @Column(name = "product_price")
    private Integer productPrice; // 商品單價
}
