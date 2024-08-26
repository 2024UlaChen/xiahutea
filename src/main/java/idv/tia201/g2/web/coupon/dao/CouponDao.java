package idv.tia201.g2.web.coupon.dao;

import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponDao extends JpaRepository<Coupon,Integer> {
}
