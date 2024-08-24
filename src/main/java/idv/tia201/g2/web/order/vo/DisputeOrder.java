package idv.tia201.g2.web.order.vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dispute_order", schema = "xiahu_db")
public class DisputeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispute_order_id")
    private Integer disputeOrderId; // 爭議編號

    @Column(name = "order_id")
    private Integer orderId; // 訂單編號
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Orders order;

    @Column(name = "customer_id")  // 單向多對一
    private Integer customerId;    // 顧客編號
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "dispute_reason")
    private String disputeReason; // 爭議原因

    @Column(name = "dispute_status", nullable = false)
    private Integer disputeStatus; // 爭議狀態

    @Column(name = "refund_amount")
    private Integer refundAmount; // 退款金額

    @Column(name = "reject_reason")
    private String rejectReason; // 不同意原因

    @Column(name = "dispute_notes")
    private String disputeNotes; // 爭議備註

    @Column(name = "apply_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp applyDatetime; // 申請日期

    @Column(name = "update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateDatetime; // 更新日期
}
