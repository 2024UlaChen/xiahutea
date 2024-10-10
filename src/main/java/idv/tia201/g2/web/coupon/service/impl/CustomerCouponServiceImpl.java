package idv.tia201.g2.web.coupon.service.impl;

import idv.tia201.g2.web.coupon.dao.CustomerCouponDao;
import idv.tia201.g2.web.coupon.service.CustomerCouponService;
import idv.tia201.g2.web.coupon.vo.CustomerCoupons;
import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerCouponServiceImpl implements CustomerCouponService {
    @Autowired
    private CustomerCouponDao customerCouponDao;

    @Autowired
    private MemberDao memberDao;

    @Override
    public void updateCouponQuantity(Integer customerId, Integer couponId, Integer newQuantity) {
        CustomerCoupons customerCoupons = customerCouponDao.findByCustomerIdAndCouponId(customerId, couponId);
        if (customerCoupons != null) {
            // 修改數量
            customerCoupons.setCouponQuantity(newQuantity);
            // 儲存更新
            customerCouponDao.save(customerCoupons);
        } else {
            throw new RuntimeException("無法找到對應的優惠券資料");
        }
    }

    @Override
    public List<CustomerCoupons> getCustomerCoupons(Integer customerId) {
        return customerCouponDao.findByCustomerId(customerId);
    }

    @Override
    public List<CustomerCoupons> getCustomerCouponsForShow(Integer customerId) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return customerCouponDao.findByCustomerIdAndCouponQuantityGreaterThanAndCoupon_ExpiredDateGreaterThanAndCoupon_CouponStatus(customerId,0,now,true);
    }

    @Override
    public CustomerCoupons addResgiterCoupon(Integer customerId) {
        

        CustomerCoupons ForResgiter = new CustomerCoupons();
        ForResgiter.setCustomerId(customerId);
        ForResgiter.setCouponId(1);
        ForResgiter.setCouponQuantity(1);
        return customerCouponDao.save(ForResgiter);
    }

    @Override
    public void SendCouponsToMembers(Integer couponId) {
        //取得目前的會員
        List<Member> memberList = memberDao.findAllMember();

        List<CustomerCoupons> list = new ArrayList<CustomerCoupons>();


        for (Member member : memberList) {
            CustomerCoupons customerCoupons = new CustomerCoupons();
            customerCoupons.setCouponId(couponId);
            customerCoupons.setCouponQuantity(1);
            customerCoupons.setCustomerId(member.getCustomerId());
            list.add(customerCoupons);
        }
        customerCouponDao.saveAll(list);
    }

}
