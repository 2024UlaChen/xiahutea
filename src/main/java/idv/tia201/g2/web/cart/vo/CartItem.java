package idv.tia201.g2.web.member.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    private Integer cartItemId;
    private String sugar;
    private String temperature;
    private String addMaterials;
    private Integer quantity;
    private Integer unitPrice;
}
