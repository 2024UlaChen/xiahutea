package idv.tia201.g2.web.order.util;

import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMappingUtil {

    public OrderDto createOrderDto(Orders order, DisputeOrder disputeOrder, List<OrderDetail> orderDetails) {
        OrderDto orderDto = new OrderDto();

        // 複製 Orders 屬性
        Orders orderCopy = new Orders();
        BeanUtils.copyProperties(order, orderCopy);
        orderDto.setOrders(orderCopy);

        // 複製 DisputeOrder 屬性
        if (disputeOrder != null) {
            DisputeOrder disputeOrderCopy = new DisputeOrder();
            BeanUtils.copyProperties(disputeOrder, disputeOrderCopy);
            orderDto.setDisputeOrder(disputeOrderCopy);
        }

        // 複製 OrderDetail 屬性
        List<OrderDetail> orderDetailCopies = orderDetails.stream()
                .map(orderDetail -> {
                    OrderDetail orderDetailCopy = new OrderDetail();
                    BeanUtils.copyProperties(orderDetail, orderDetailCopy);
                    return orderDetailCopy;
                })
                .collect(Collectors.toList());
        orderDto.setOrderDetails(orderDetailCopies);

        return orderDto;
    }
}
