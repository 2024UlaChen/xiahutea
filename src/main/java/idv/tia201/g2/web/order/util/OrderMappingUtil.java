package idv.tia201.g2.web.order.util;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.order.dto.OMemberDto;
import idv.tia201.g2.web.order.dto.OStoreDto;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMappingUtil {




    public OrderDto createOrderDto(
            Orders order, DisputeOrder disputeOrder, List<OrderDetail> orderDetails,
            Member member , Store store
    ) {
        OrderDto orderDto = new OrderDto();

        // 複製 Orders 屬性
        Orders orderCopy = new Orders();
        BeanUtils.copyProperties(order, orderCopy);
        orderDto.setOrders(orderCopy);

        // 複製 DisputeOrder 屬性
        DisputeOrder disputeOrderCopy = new DisputeOrder();
        BeanUtils.copyProperties(disputeOrder, disputeOrderCopy);
        orderDto.setDisputeOrder(disputeOrderCopy);

        // 複製 OrderDetail 屬性
        List<OrderDetail> orderDetailCopies = orderDetails.stream()
                .map(orderDetail -> {
                    OrderDetail orderDetailCopy = new OrderDetail();
                    BeanUtils.copyProperties(orderDetail, orderDetailCopy);
                    return orderDetailCopy;
                })
                .collect(Collectors.toList());
        orderDto.setOrderDetails(orderDetailCopies);

        // 複製 MemberDTO 屬性
        OMemberDto oMemberDto = createMemberDto(member);
        orderDto.setOMemberDto(oMemberDto);

        // 複製 StoreDTO 屬性
        OStoreDto oStoreDto = createStoreDto(store);
        orderDto.setOStoreDto(oStoreDto);

        return orderDto;
    }

    private OMemberDto createMemberDto(Member member) {
        OMemberDto oMemberDto = new OMemberDto();
        BeanUtils.copyProperties(member, oMemberDto);
        return oMemberDto;
    }

    private OStoreDto createStoreDto(Store store) {
        OStoreDto oStoreDto = new OStoreDto();
        BeanUtils.copyProperties(store, oStoreDto);
        return oStoreDto;
    }
}
