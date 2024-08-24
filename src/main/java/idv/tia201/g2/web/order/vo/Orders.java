package idv.tia201.g2.web.order.vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.print.Book;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders", schema = "xiahu_db")
public class Orders implements Serializable {

    //todo 確認extend & uid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;          // 訂單編號

    @OneToOne(mappedBy = "order_id")
    private OrderDetail orderDetail;  // 訂單明細mapping


    // todo 待GIT
    @Column(name = "customer_id")  // 單向多對一
    private Integer customerId;    // 顧客編號
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "customer_money_discount")
    private Integer customerMoneyDiscount; // 錢包折抵金額

    @Column(name = "order_status", nullable = false)
    private Integer orderStatus; // 訂單狀態


    // todo 待git
    @Column(name = "store_id") //單向一對一
    private Integer storeId; // 商店編號
    @OneToOne
    @JoinColumn(name = "store_id", nullable = false, insertable = false, updatable = false)
    private Store store;

    // todo 待git
    @Column(name = "customer_coupons_id") //單向一對一
    private Integer customerCouponsId;    // 優惠券編號
    @OneToOne
    @JoinColumn(name = "customer_coupons_id", nullable = false, insertable = false, updatable = false)
    private CustomerCoupons customerCoupon;


    @Column(name = "coupon_discount")
    private Integer couponDiscount; // 優惠券折抵金額


    // todo 待git
    @Column(name = "loyalty_card_id")  //單向一對一
    private Integer loyaltyCardId;     // 集點卡編號
    @OneToOne
    @JoinColumn(name = "loyalty_card_id", nullable = false, insertable = false, updatable = false)
    private CustomerLoyaltyCard loyaltyCard;


    @Column(name = "loyalty_discount")
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
    private Timestamp receiverDatetime; // 預計取貨時間

    @Column(name = "order_score")
    private Integer orderScore; // 訂單評分

    @Column(name = "order_feedback")
    private String orderFeedback; // 訂單評分建議

    @Column(name = "order_creat_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp orderCreateDatetime; // 建立日期時間
}
