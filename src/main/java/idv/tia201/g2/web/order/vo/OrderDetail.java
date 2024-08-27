package idv.tia201.g2.web.order.vo;

import idv.tia201.g2.core.pojo.Core;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
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
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Orders order;


    // todo 待git  單向一對多
//    @OneToMany
//    @JoinColumn(name = "product_id", referencedColumnName = "order_detail_id", nullable = false)
//    private List<Product> products; // 商品編號

    @Column(name = "product_sugar")
    private String productSugar; // 甜度

    @Column(name = "product_temperature")
    private String productTemperature; // 溫度

    @Column(name = "product_add_materials")
    private String productAddMaterials; // 加料

    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity; // 商品數量

    @Column(name = "product_price", nullable = false)
    private Integer productPrice; // 商品單價
}
