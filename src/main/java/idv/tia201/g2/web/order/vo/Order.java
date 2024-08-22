package idv.tia201.g2.web.order.vo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "orders", schema = "xiahu_db")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId; // 訂單編號

    @Column(name = "customer_id", nullable = false)
    private Integer customerId; // 顧客編號

    @Column(name = "customer_money_dicount")
    private Integer customerMoneyDiscount; // 錢包折抵金額

    @Column(name = "order_status", nullable = false)
    private Integer orderStatus; // 訂單狀態

    @Column(name = "store_id")
    private Integer storeId; // 商店編號

    @Column(name = "customer_coupons_id")
    private Integer customerCouponsId; // 優惠券編號

    @Column(name = "coupon_dicount")
    private Integer couponDiscount; // 優惠券折抵金額

    @Column(name = "loyalty_card_id")
    private Integer loyaltyCardId; // 集點卡編號

    @Column(name = "loyalty_dicount")
    private Integer loyaltyDiscount; // 集點折抵金額

    @Column(name = "order_product_quantity", nullable = false)
    private Integer orderProductQuantity; // 商品總數量

    @Column(name = "product_amount", nullable = false)
    private Integer productAmount; // 商品總金額

    @Column(name = "processing_fees", nullable = false)
    private Integer processingFees = 10; // 訂單處理費

    @Column(name = "payment_method", nullable = false)
    private Integer paymentMethod; // 付款方式

    @Column(name = "payment_amount", nullable = false)
    private Integer paymentAmount; // 付款金額

    @Column(name = "Invoice_method", nullable = false)
    private Integer invoiceMethod; // 開立發票方式

    @Column(name = "Invoice_no")
    private String invoiceNo; // 處理費發票號碼

    @Column(name = "Invoice_vat")
    private Integer invoiceVat; // 發票統編

    @Column(name = "Invoice_carrier")
    private String invoiceCarrier; // 發票載具

    @Column(name = "receiver_method", nullable = false)
    private Integer receiverMethod; // 取貨方式

    @Column(name = "receiver_name", nullable = false)
    private String receiverName; // 取貨人姓名

    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone; // 取貨人手機

    @Column(name = "receiver_address")
    private String receiverAddress; // 取貨人地址

    @Column(name = "receiver_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiverDatetime; // 預計取貨時間

    @Column(name = "order_score")
    private Integer orderScore; // 訂單評分

    @Column(name = "order_feedback")
    private String orderFeedback; // 訂單評分建議

    @Column(name = "order_creat_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderCreateDatetime = new Date(); // 建立日期時間

    // Getters and Setters
}
