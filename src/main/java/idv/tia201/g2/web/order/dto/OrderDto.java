package idv.tia201.g2.web.order.dto;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Orders orders;
    private DisputeOrder disputeOrder;
    private List<OrderDetail> orderDetails;

}