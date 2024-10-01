package idv.tia201.g2.web.order.vo;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.product.vo.Product;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_detail", schema = "xiahu_db")
public class OrderDetail extends Core {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Integer orderDetailId; // 訂單明細編號

    @Column(name = "order_id")
    private Integer orderId; // 訂單編號
    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Orders orders;

    @Column(name = "product_id")
    private Integer productId; // 商品編號
    @OneToOne
    @JoinColumn(name = "product_id",  insertable = false, updatable = false)
    private Product product;

    @Column(name = "product_sugar", updatable = false)
    private String productSugar; // 甜度

    @Column(name = "product_temperature", updatable = false)
    private String productTemperature; // 溫度

    @Column(name = "product_add_materials", updatable = false)
    private String productAddMaterials = ""; // 加料

    @Column(name = "product_quantity", updatable = false)
    private Integer productQuantity; // 商品數量

    @Column(name = "product_price", updatable = false)
    private Integer productPrice; // 商品單價
}
