package idv.tia201.g2.web.order.service.impl;

import idv.tia201.g2.core.util.ValidateUtil;
import idv.tia201.g2.web.coupon.dao.CustomerCouponDao;
import idv.tia201.g2.web.coupon.service.CustomerCouponService;
import idv.tia201.g2.web.coupon.vo.CustomerCoupons;
import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.dao.MemberLoyaltyCardRepository;
import idv.tia201.g2.web.member.service.MemberLoyaltyCardService;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.dao.OrderDetailDao;
import idv.tia201.g2.web.order.dao.OrderRepository;
import idv.tia201.g2.web.order.dto.NotificationDto;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.InvoiceService;
import idv.tia201.g2.web.order.service.NotificationService;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.util.OrderMappingUtil;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private DisputeDao disputeDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private MemberLoyaltyCardRepository memberLoyaltyCardRepository;
    @Autowired
    private CustomerCouponDao customerCouponDao;

    @Autowired
    private OrderMappingUtil orderMappingUtil;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberLoyaltyCardService memberLoyaltyCardService;
    @Autowired
    private CustomerCouponService customerCouponService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private OrderRepository orderRepository;

    // -------- FINISH ---------------------------------
    // 前台 訂單新增
    @Override
    public OrderDto addOrder(Orders order, List<OrderDetail> orderDetails) {
        OrderDto orderDto = new OrderDto();

        //訂單檢查
        int customerId = order.getCustomerId();
        Member member = memberDao.findMemberById(customerId);
        if(member == null) {
            orderDto.setMessage("無此會員ID");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        int storeId = order.getStoreId();
        Store store = storeDao.findByStoreId(storeId);
        if(store == null) {
            orderDto.setMessage("無此商店ID");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        if(order.getOrderProductQuantity() < 0 || order.getProductAmount() < 0 ){
            orderDto.setMessage("商品數量或商品總金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        if(order.getPaymentMethod() != 1 || isEmpty(order.getPaymentAmount())){
            orderDto.setMessage("付款方法或付款金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        if(order.getInvoiceMethod() != 1 && order.getInvoiceMethod() != 2 && order.getInvoiceMethod() != 3){
            orderDto.setMessage("發票方式錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        final String invoiceCarrier = order.getInvoiceCarrier();
        final String invoiceVat = order.getInvoiceVat() + "";
        if(order.getInvoiceMethod() == 1){  // 手機載具
            if(isEmpty(invoiceCarrier) || invoiceCarrier.charAt(0) != '/' || invoiceCarrier.trim().length() != 8){
                orderDto.setMessage("手機載具輸入錯誤");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            for(int i = 1; i < invoiceCarrier.length(); i++){
                char CarrierChar = invoiceCarrier.charAt(i);
                boolean isMark = (CarrierChar == 43 || CarrierChar == 45 || CarrierChar == 46);
                boolean isNum = (CarrierChar>=48 && CarrierChar<=57);
                boolean isEng = (CarrierChar>=65 && CarrierChar<=90);
                if(!(isMark || isNum || isEng)){
                    orderDto.setMessage("載具輸入錯誤");
                    orderDto.setSuccessful(false);
                    return orderDto;
                }
            }
        }
        if (order.getInvoiceMethod() == 3) {  // 統編
            if(!(ValidateUtil.isValidTWBID(invoiceVat))){
                orderDto.setMessage("統編輸入錯誤");
                orderDto.setSuccessful(false);
                return orderDto;
            }
        }
        if(
            isEmpty(order.getReceiverMethod()) || isEmpty(order.getReceiverName()) ||
            isEmpty(order.getReceiverPhone()) || isEmpty(order.getReceiverDatetime())
        ){
            orderDto.setMessage("未輸入取貨資訊");
            orderDto.setSuccessful(false);
            return orderDto;
        }

        // 訂單明細檢查
        for (OrderDetail orderDetail : orderDetails) {
            int productId = orderDetail.getProductId();
            Product product = productDao.findByProductId(productId);
            if(product == null){
                orderDto.setMessage("商品編號錯誤");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            if(isEmpty(orderDetail.getProductSugar()) || isEmpty(orderDetail.getProductTemperature())){
                orderDto.setMessage("未輸入甜度溫度");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            if(orderDetail.getProductQuantity() < 0 || orderDetail.getProductPrice() < 0){
                orderDto.setMessage("商品數量或商品金額錯誤");
                orderDto.setSuccessful(false);
                return orderDto;
            }
        }
        if(
            order.getCouponDiscount() < 0 || order.getLoyaltyDiscount() < 0 || order.getCustomerMoneyDiscount() < 0 ||
            order.getCustomerMoneyDiscount() > member.getCustomerMoney()
        ){
            orderDto.setMessage("折抵金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        // 會員使用集點卡
        Integer loyaltyCardId = order.getLoyaltyCardId();
        if(loyaltyCardId != null) {
            CustomerLoyaltyCard customerLoyaltyCard = memberLoyaltyCardRepository.findByLoyaltyCardId(loyaltyCardId);
            if (customerLoyaltyCard == null) {
                orderDto.setMessage("無此集點卡");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            memberLoyaltyCardService.UpdatePoints(loyaltyCardId, order.getLoyaltyDiscount());
        }
        // 會員使用優惠券
        Integer customerCouponsId = order.getCustomerCouponsId();
        if(customerCouponsId != null) {
            CustomerCoupons customerCoupon = customerCouponDao.findByCustomerIdAndCustomerCouponsId(customerId, customerCouponsId);
            if (customerCoupon == null) {
                orderDto.setMessage("無此優惠券");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            customerCouponService.updateCouponQuantity(customerId, customerCoupon.getCouponId() , customerCoupon.getCouponQuantity() - 1);
        }
        // 會員使用點數
        memberService.updateMemberMoneyById(customerId, - order.getCustomerMoneyDiscount());

        order.setOrderStatus(1);
        order.setOrderCreateDatetime(new Timestamp(System.currentTimeMillis()));
        order.setSuccessful(true);
        orderDao.insert(order);
        // 存商品
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setSuccessful(true);
            orderDetailDao.insert(orderDetail);
        }

        // 傳送發票參數給綠界
        String addInvoiceNo = invoiceService.createInvoice(order);
        if(addInvoiceNo == null){
            orderDto.setMessage("開立發票失敗");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        //存發票
        orderDao.saveInvoiceNo(order.getOrderId(), addInvoiceNo);

        orderDto.setMessage("訂單新增成功");
        orderDto.setSuccessful(true);
        orderDto.setOrders(order);
        orderDto.setOrderDetails(orderDetails);

        // 建立通知的內容
        NotificationDto notificationDto = new NotificationDto(order.getOrderId(), order.getOrderCreateDatetime());
        notificationService.notifyNewOrder(notificationDto);
        return orderDto;
    }

    // 前台 訂單列表 顯示
    @Override
    public Page<OrderDto> findByCustomerId(int customerId, Pageable pageable) {
        Page<Orders> orders = orderRepository.findByCustomerId(customerId, pageable);

        //                      建立流             || 映射每個訂單
        List<OrderDto> orderDtos = orders.stream().map(order -> {
            DisputeOrder disputeOrder = disputeDao.selectByOrderId(order.getOrderId());
            List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(order.getOrderId());
            return orderMappingUtil.createOrderDto(order, disputeOrder, orderDetails);
            // 收集結果
        }).collect(Collectors.toList());

        // 回傳Page<OrderDto>
        return new PageImpl<>(orderDtos, pageable, orders.getTotalElements());
    }

    public Page<OrderDto> findByCustomerIdAndDateRange(Integer customerId, Integer orderStatus, Timestamp dateStart, Timestamp dateEnd, Pageable pageable) {
        Page<Orders> orders;
        // 根據 customerId 和訂單建立日期範圍來過濾訂單
        if (orderStatus != null) {
            orders = orderRepository.findByCustomerIdAndOrderStatusAndOrderCreateDatetimeBetween(customerId, orderStatus, dateStart, dateEnd, pageable);
        }else {
            orders = orderRepository.findByCustomerIdAndOrderCreateDatetimeBetween(customerId, dateStart, dateEnd, pageable);
        }
        //                      建立流             || 映射每個訂單
        List<OrderDto> orderDtos = orders.stream().map(order -> {
            DisputeOrder disputeOrder = disputeDao.selectByOrderId(order.getOrderId());
            List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(order.getOrderId());
            return orderMappingUtil.createOrderDto(order, disputeOrder, orderDetails);
            // 收集結果
        }).collect(Collectors.toList());
        return new PageImpl<>(orderDtos, pageable, orders.getTotalElements());
    }


    // 前台 訂單明細 顯示
    @Override
    public OrderDto findByMemberOrderId(int orderId){
        Orders orders = orderDao.selectByOrderId(orderId);
        if(orders == null){
            return null;
        }
        DisputeOrder disputeOrder = disputeDao.selectByOrderId(orders.getOrderId());
        List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(orderId);
        return orderMappingUtil.createOrderDto(orders, disputeOrder, orderDetails);
    }

    // 前台 訂單明細 新增評分
    @Override
    public Orders addStar(Orders newOrder){
        final Orders oldOrder = orderDao.selectByOrderId(newOrder.getOrderId());
        final int ordersStar = newOrder.getOrderScore();
        final String orderFeedBack = newOrder.getOrderFeedback();
        if(ordersStar == 0){
            newOrder.setMessage("未輸入評分");
            newOrder.setSuccessful(false);
            return newOrder;
        }
        if(!(isEmpty(oldOrder.getOrderScore()))){
            newOrder.setMessage("不可重複評分");
            newOrder.setSuccessful(false);
            return newOrder;
        }
        oldOrder.setOrderScore(ordersStar);
        oldOrder.setOrderFeedback(orderFeedBack);
        orderDao.update(oldOrder);
        newOrder.setMessage("評分完成");
        newOrder.setSuccessful(true);
        return newOrder;
    }

    // 後台 訂單列表 顯示
    @Override
    public Page<Orders> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    // 後台 訂單明細 顯示
    @Override
    public List<OrderDetail> findByOrderId(int orderId) {
        return orderDetailDao.selectByOrderId(orderId);
    }

    // 後台 訂單明細 修改狀態
    @Override
    public Orders updateStatus(Orders newOrder) {
        final Orders oldOrder = orderDao.selectByOrderId(newOrder.getOrderId());
        if(oldOrder.getOrderStatus() >= newOrder.getOrderStatus() ){ //舊訂單狀態值 > 新訂單狀態值
            newOrder.setMessage("修改失敗");
            newOrder.setSuccessful(false);
            return newOrder;
        }
        oldOrder.setOrderStatus(newOrder.getOrderStatus());
        orderDao.update(oldOrder);
        newOrder.setMessage("修改完成");
        newOrder.setSuccessful(true);
        return newOrder;
    }
}