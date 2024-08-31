package idv.tia201.g2.web.coupon.vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name="coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id",nullable = false,updatable = false)
    private Integer couponId;
    //優惠券名稱不該重複 中文編碼大約占3bytes(開100) 不能空
    @Column(name="coupon_name",unique = true,nullable = false,length = 30)
    private String couponName;

    @Column(name="coupon_rule", nullable = false)
    private String couponRule;

    @Column(nullable = false)
    private Integer discount;

    @Column(name = "coupon_status", nullable = false)
    private Boolean couponStatus;
    //儲存 年 月 日 十 分 秒
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    private Timestamp createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired_date")
    private Timestamp expiredDate;

    //和消費者優惠券紀錄表 一對多
    //設定一對多  夭壽 千萬不要
//    @OneToMany(mappedBy = "coupon") //mapping到 優惠券紀錄表的coupon屬性
//    private List<CustomerCoupons> customerCoupons;

}
