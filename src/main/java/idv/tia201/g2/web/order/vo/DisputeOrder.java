package idv.tia201.g2.web.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.member.vo.Member;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dispute_order", schema = "xiahu_db")
public class DisputeOrder extends Core {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispute_order_id", updatable = false)
    private Integer disputeOrderId; // 爭議編號

    @Column(name = "order_id")
    private Integer orderId; // 訂單編號
    @OneToOne
    @JoinColumn(name = "order_id", insertable = false,updatable = false)
    private Orders order;

    @Column(name = "dispute_status")
    private Integer disputeStatus; // 爭議狀態

    @Column(name = "customer_id")  // 單向多對一
    private Integer customerId;    // 顧客編號
    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Member customer;

    @Column(name = "dispute_reason", updatable = false)
    private String disputeReason; // 爭議原因

    @Column(name = "refund_amount")
    private Integer refundAmount; // 退款金額

    @Column(name = "reject_reason")
    private String rejectReason; // 不同意原因

    @Column(name = "dispute_notes")
    private String disputeNotes; // 內部備註

    @Column(name = "apply_datetime", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Timestamp applyDatetime; // 申請日期

    @Column(name = "update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Timestamp updateDatetime; // 更新日期
}
