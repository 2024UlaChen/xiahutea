package idv.tia201.g2.web.coupon.vo;


import idv.tia201.g2.web.member.vo.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="customer_coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCoupons {
//待討論 應該放在哪一層資料夾
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_coupons_id", nullable = false, updatable = false)
    private Integer customerCouponsId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "coupon_id", nullable = false)
    private Integer couponId;
    //優惠券數量
    @Column(name = "coupon_quantity", nullable = false)
    private Integer couponQuantity;
    //多對一 關連到優惠券 關聯欄位是coupon_id
    @ManyToOne
    @JoinColumn(name = "coupon_id", insertable = false, updatable = false)
    private Coupon coupon;
    //多對一 關連到消費者 關聯欄位是customer_id
    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Member member;


}
