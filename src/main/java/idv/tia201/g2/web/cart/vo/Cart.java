package idv.tia201.g2.web.member.vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id",updatable = false)
    private Integer cartItemId;
    @Column(name ="cart_item_customer_id")
    private Integer customerId;
    @Column(name ="cart_item_product_id")
    private Integer productId;
    private String sugar;
    private String temperature;
    @Column(name="add_materials",nullable = true)
    private String addMaterials;
    private Integer quantity;
    @Column(name="unit_price")
    private Integer unitPrice;
}
