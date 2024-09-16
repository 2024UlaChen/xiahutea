package idv.tia201.g2.web.coupon.dao;

import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponDao extends JpaRepository<Coupon,Integer> {
    public Coupon findByCouponName(String couponName);
    @Query("SELECT c FROM Coupon c WHERE c.couponId IN " +
            "(SELECT cc.couponId" + " FROM CustomerCoupons cc " +
            "WHERE cc.customerId = :customerId)")
    public List<Coupon> findCouponIdsByCutomerId(Integer customerId);
}
