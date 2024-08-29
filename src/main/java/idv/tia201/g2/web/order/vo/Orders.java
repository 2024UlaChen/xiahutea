package idv.tia201.g2.web.order.vo;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.coupon.vo.CustomerCoupons;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import idv.tia201.g2.web.store.vo.Store;
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
public class Orders extends Core {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;          // 訂單編號

    @OneToOne(mappedBy = "order_id")
    private OrderDetail orderDetail;  // 訂單明細mapping

    @Column(name = "customer_id")  // 單向多對一
    private Integer customerId;    // 顧客編號
    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Member customer;

    @Column(name = "customer_money_discount")
    private Integer customerMoneyDiscount; // 錢包折抵金額

    @Column(name = "order_status")
    private Integer orderStatus; // 訂單狀態

    @Column(name = "store_id") //單向一對一
    private Integer storeId; // 商店編號
    @OneToOne
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Store store;

    @Column(name = "customer_coupons_id") //單向一對一
    private Integer customerCouponsId;    // 優惠券編號
    @OneToOne
    @JoinColumn(name = "customer_coupons_id", insertable = false, updatable = false)
    private CustomerCoupons customerCoupon;

    @Column(name = "coupon_discount")
    private Integer couponDiscount; // 優惠券折抵金額

    @Column(name = "loyalty_card_id")  //單向一對一
    private Integer loyaltyCardId;     // 集點卡編號
    @OneToOne
    @JoinColumn(name = "loyalty_card_id", insertable = false, updatable = false)
    private CustomerLoyaltyCard loyaltyCard;

    @Column(name = "loyalty_discount")
    private Integer loyaltyDiscount; // 集點折抵金額

    @Column(name = "order_product_quantity")
    private Integer orderProductQuantity; // 商品總數量

    @Column(name = "product_amount")
    private Integer productAmount; // 商品總金額

    @Column(name = "processing_fees")
    private Integer processingFees = 10; // 訂單處理費

    @Column(name = "payment_method")
    private Integer paymentMethod; // 付款方式

    @Column(name = "payment_amount")
    private Integer paymentAmount; // 付款金額

    @Column(name = "Invoice_method")
    private Integer invoiceMethod; // 開立發票方式

    @Column(name = "Invoice_no")
    private String invoiceNo; // 處理費發票號碼

    @Column(name = "Invoice_vat")
    private Integer invoiceVat; // 發票統編

    @Column(name = "Invoice_carrier")
    private String invoiceCarrier; // 發票載具

    @Column(name = "receiver_method")
    private Integer receiverMethod; // 取貨方式

    @Column(name = "receiver_name")
    private String receiverName; // 取貨人姓名

    @Column(name = "receiver_phone")
    private String receiverPhone; // 取貨人手機

    @Column(name = "receiver_address")
    private String receiverAddress; // 取貨人地址

    @Column(name = "receiver_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp receiverDatetime; // 預計取貨時間

    @Column(name = "order_score")
    private Integer orderScore; // 訂單評分

    @Column(name = "order_feedback")
    private String orderFeedback; // 訂單評分建議

    @Column(name = "order_creat_datetime", insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp orderCreateDatetime; // 建立日期時間
}
