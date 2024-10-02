package idv.tia201.g2.web.order.vo;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.store.vo.Store;
import lombok.*;
import jakarta.persistence.*;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders", schema = "xiahu_db")
public class Orders extends Core {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false)
    private Integer orderId;          // 訂單編號

    @Column(name = "order_status")
    private Integer orderStatus; // 訂單狀態

    @Column(name = "customer_id")  // 單向多對一
    private Integer customerId;    // 顧客編號
    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Member customer;

    @Column(name = "customer_money_discount", updatable = false)
    private Integer customerMoneyDiscount = 0; // 錢包折抵金額

    @Column(name = "store_id") //單向一對一
    private Integer storeId; // 商店編號
    @OneToOne
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Store store;

    @Column(name = "customer_coupons_id") //單向一對一
    private Integer customerCouponsId;    // 優惠券編號

    @Column(name = "coupon_discount", updatable = false)
    private Integer couponDiscount = 0; // 優惠券折抵金額

    @Column(name = "loyalty_card_id")  //單向一對一
    private Integer loyaltyCardId;     // 集點卡編號

    @Column(name = "loyalty_discount", updatable = false)
    private Integer loyaltyDiscount = 0; // 集點折抵金額

    @Column(name = "order_product_quantity", updatable = false)
    private Integer orderProductQuantity; // 商品總數量

    @Column(name = "product_amount", updatable = false)
    private Integer productAmount; // 商品總金額

    @Column(name = "processing_fees", updatable = false)
    private Integer processingFees = 10; // 訂單處理費

    @Column(name = "payment_method", updatable = false)
    private Integer paymentMethod; // 付款方式

    @Column(name = "payment_amount", updatable = false)
    private Integer paymentAmount; // 付款金額

    @Column(name = "Invoice_method", updatable = false)
    private Integer invoiceMethod; // 開立發票方式

    @Column(name = "Invoice_no", insertable = false, updatable = false)
    private String invoiceNo; // 處理費發票號碼

    @Column(name = "Invoice_vat", updatable = false)
    private Integer invoiceVat; // 發票統編

    @Column(name = "Invoice_carrier", updatable = false)
    private String invoiceCarrier; // 發票載具

    @Column(name = "receiver_method", updatable = false)
    private Integer receiverMethod; // 取貨方式

    @Column(name = "receiver_name", updatable = false)
    private String receiverName; // 取貨人姓名

    @Column(name = "receiver_phone", updatable = false)
    private String receiverPhone; // 取貨人手機

    @Column(name = "receiver_address", updatable = false)
    private String receiverAddress; // 取貨人地址

    @Column(name = "receiver_datetime", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Timestamp receiverDatetime; // 預計取貨時間

    @Column(name = "order_score")
    private Integer orderScore; // 訂單評分

    @Column(name = "order_feedback")
    private String orderFeedback; // 訂單評分建議

    @Column(name = "order_note", updatable = false)
    private  String orderNote;  //訂單備註

    @Column(name = "order_creat_datetime", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Timestamp orderCreateDatetime; // 建立日期時間

    @Column(name = "order_update_datetime", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Timestamp orderUpdateDatetime; // 訂單更新時間

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", orderScore=" + orderScore +
                ", orderFeedback='" + orderFeedback + '\'' +
                '}';
    }
}