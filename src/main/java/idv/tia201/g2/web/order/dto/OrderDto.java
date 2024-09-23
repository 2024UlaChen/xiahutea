package idv.tia201.g2.web.order.dto;

import java.util.List;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends Core {

    private Orders orders;
    private DisputeOrder disputeOrder;
    private List<OrderDetail> orderDetails;

}