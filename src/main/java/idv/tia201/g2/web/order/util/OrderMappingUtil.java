package idv.tia201.g2.web.order.util;

import idv.tia201.g2.web.order.dto.OrderDetailDto;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMappingUtil {
    // DAO和DTO互相轉換

    // 用在DTO @builder簡化程式碼的Set
    public OrderDto mapToOrderDto(Orders order, List<OrderDetail> orderDetails, DisputeOrder disputeOrder) {
        List<OrderDetailDto> orderDetailDto = orderDetails.stream().map(orderDetail ->
                OrderDetailDto.builder()
                        .orderDetailId(orderDetail.getOrderDetailId())
                        .productId(orderDetail.getProductId())
                        .productSugar(orderDetail.getProductSugar())
                        .productTemperature(orderDetail.getProductTemperature())
                        .productAddMaterials(orderDetail.getProductAddMaterials())
                        .productQuantity(orderDetail.getProductQuantity())
                        .productPrice(orderDetail.getProductPrice())
                        .build()
        ).collect(Collectors.toList());

        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .customerId(order.getCustomerId())
                .customerMoneyDiscount(order.getCustomerMoneyDiscount())
                .storeId(order.getStoreId())
                .loyaltyCardId(order.getLoyaltyCardId())
                .loyaltyDiscount(order.getLoyaltyDiscount())
                .customerCouponsId(order.getCustomerCouponsId())
                .couponDiscount(order.getCouponDiscount())
                .orderProductQuantity(order.getOrderProductQuantity())
                .productAmount(order.getProductAmount())
                .processingFees(order.getProcessingFees())
                .paymentMethod(order.getPaymentMethod())
                .paymentAmount(order.getPaymentAmount())
                .invoiceMethod(order.getInvoiceMethod())
                .invoiceNo(order.getInvoiceNo())
                .invoiceVat(order.getInvoiceVat())
                .invoiceCarrier(order.getInvoiceCarrier())
                .receiverMethod(order.getReceiverMethod())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .receiverAddress(order.getReceiverAddress())
                .receiverDatetime(order.getReceiverDatetime())
                .orderNote(order.getOrderNote())
                .orderScore(order.getOrderScore())
                .orderFeedback(order.getOrderFeedback())
                .orderCreateDatetime(order.getOrderCreateDatetime())
                .orderUpdateDatetime(order.getOrderUpdateDatetime())
                .orderDetails(orderDetailDto)
                .disputeOrderId(disputeOrder.getDisputeOrderId())
                .disputeStatus(disputeOrder.getDisputeStatus())
                .disputeReason(disputeOrder.getDisputeReason())
                .refundAmount(disputeOrder.getRefundAmount())
                .rejectReason(disputeOrder.getRejectReason())
                .disputeNotes(disputeOrder.getDisputeNotes())
                .applyDatetime(disputeOrder.getApplyDatetime())
                .updateDatetime(disputeOrder.getUpdateDatetime())
                .build();
    }

    public Orders mapToOrder(OrderDto orderDto) {
        return Orders.builder()
                .orderId(orderDto.getOrderId())
                .orderStatus(orderDto.getOrderStatus())
                .customerId(orderDto.getCustomerId())
                .customerMoneyDiscount(orderDto.getCustomerMoneyDiscount())
                .storeId(orderDto.getStoreId())
                .loyaltyCardId(orderDto.getLoyaltyCardId())
                .loyaltyDiscount(orderDto.getLoyaltyDiscount())
                .customerCouponsId(orderDto.getCustomerCouponsId())
                .couponDiscount(orderDto.getCouponDiscount())
                .orderProductQuantity(orderDto.getOrderProductQuantity())
                .productAmount(orderDto.getProductAmount())
                .processingFees(orderDto.getProcessingFees())
                .paymentMethod(orderDto.getPaymentMethod())
                .paymentAmount(orderDto.getPaymentAmount())
                .invoiceMethod(orderDto.getInvoiceMethod())
                .invoiceNo(orderDto.getInvoiceNo())
                .invoiceVat(orderDto.getInvoiceVat())
                .invoiceCarrier(orderDto.getInvoiceCarrier())
                .receiverMethod(orderDto.getReceiverMethod())
                .receiverName(orderDto.getReceiverName())
                .receiverPhone(orderDto.getReceiverPhone())
                .receiverAddress(orderDto.getReceiverAddress())
                .receiverDatetime(orderDto.getReceiverDatetime())
                .orderNote(orderDto.getOrderNote())
                .orderScore(orderDto.getOrderScore())
                .orderFeedback(orderDto.getOrderFeedback())
                .orderCreateDatetime(orderDto.getOrderCreateDatetime())
                .orderUpdateDatetime(orderDto.getOrderUpdateDatetime())
                .build();
    }

    public DisputeOrder mapToDisputeOrder(OrderDto orderDto) {
        return DisputeOrder.builder()
                .disputeOrderId(orderDto.getDisputeOrderId())
                .disputeStatus(orderDto.getDisputeStatus())
                .disputeReason(orderDto.getDisputeReason())
                .refundAmount(orderDto.getRefundAmount())
                .rejectReason(orderDto.getRejectReason())
                .disputeNotes(orderDto.getDisputeNotes())
                .applyDatetime(orderDto.getApplyDatetime())
                .updateDatetime(orderDto.getUpdateDatetime())
                .build();
    }

    public List<OrderDetail> mapToOrderDetails(OrderDto orderDto) {
        return orderDto.getOrderDetails().stream().map(orderDetailDto ->
                OrderDetail.builder()
                        .orderDetailId(orderDetailDto.getOrderDetailId())
                        .productId(orderDetailDto.getProductId())
                        .productSugar(orderDetailDto.getProductSugar())
                        .productTemperature(orderDetailDto.getProductTemperature())
                        .productAddMaterials(orderDetailDto.getProductAddMaterials())
                        .productQuantity(orderDetailDto.getProductQuantity())
                        .productPrice(orderDetailDto.getProductPrice())
                        .build()
        ).collect(Collectors.toList());
    }
}
