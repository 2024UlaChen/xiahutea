package idv.tia201.g2.web.order.dto;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class OrderDto {

    private Integer orderId;          // 訂單編號
    private Integer orderStatus;         // 訂單狀態

    private Integer customerId;            // 顧客編號
    private Integer customerMoneyDiscount; // 錢包折抵金額

    private Integer storeId;            // 商店編號
    private Integer loyaltyCardId;     // 集點卡編號
    private Integer loyaltyDiscount;    // 集點折抵金額

    private Integer customerCouponsId;    // 優惠券編號
    private Integer couponDiscount;        // 優惠券折抵金額

    private Integer orderProductQuantity; // 商品總數量
    private Integer productAmount;         // 商品總金額

    private Integer processingFees = 10; // 訂單處理費

    private Integer paymentMethod;      // 付款方式
    private Integer paymentAmount;      // 付款金額

    private Integer invoiceMethod;      // 開立發票方式
    private String invoiceNo;           // 處理費發票號碼
    private Integer invoiceVat;         // 發票統編
    private String invoiceCarrier;      // 發票載具

    private Integer receiverMethod;         // 取貨方式
    private String receiverName;            // 取貨人姓名
    private String receiverPhone;           // 取貨人手機
    private String receiverAddress;         // 取貨人地址
    private Timestamp receiverDatetime;     // 預計取貨時間
    private String orderNote;               //訂單備註

    private Integer orderScore;             // 訂單評分
    private String orderFeedback;           // 訂單評分建議

    private Timestamp orderCreateDatetime; // 建立日期時間
    private Timestamp orderUpdateDatetime; // 訂單更新時間

    //---------orderDetail---------------------------------
    private List<OrderDetailDto> orderDetails;

    //---------DisputeOrder----------------------------------

    private Integer disputeOrderId;     // 爭議編號
    private Integer disputeStatus;      // 爭議狀態

    private String disputeReason;       // 爭議原因
    private Integer refundAmount;       // 退款金額
    private String rejectReason;        // 不同意原因
    private String disputeNotes;        // 內部備註

    private Timestamp applyDatetime;    // 爭議申請日期
    private Timestamp updateDatetime;   // 爭議更新日期
}