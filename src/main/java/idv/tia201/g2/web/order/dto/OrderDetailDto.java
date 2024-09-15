package idv.tia201.g2.web.order.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class OrderDetailDto {
    private Integer orderDetailId;
    private Integer productId;
    private String productSugar;
    private String productTemperature;
    private String productAddMaterials;
    private Integer productQuantity;
    private Integer productPrice;
}
