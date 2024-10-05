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
import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    private OrderRepository orderRepository;
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
    private StoreService storeService;
    @Autowired
    private MemberLoyaltyCardService memberLoyaltyCardService;
    @Autowired
    private CustomerCouponService customerCouponService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private InvoiceService invoiceService;


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
        if(order.getPaymentMethod() != 1 || isEmpty(order.getPaymentAmount()) || order.getPaymentAmount() < 0){
            orderDto.setMessage("付款方法或付款金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        // 判斷發票
        Integer invoiceMethod = order.getInvoiceMethod();
        String invoiceCarrier = order.getInvoiceCarrier();
        String invoiceVat = order.getInvoiceVat() + "";
        if(invoiceMethod != 1 && invoiceMethod != 2 && invoiceMethod != 3){
            orderDto.setMessage("發票方式錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        if(invoiceMethod == 1){  // 手機載具
            if (order.getInvoiceCarrier() == null ||  order.getInvoiceVat() != null){
                orderDto.setMessage("手機載具需輸入載具號碼，且不可輸入統編");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            if(invoiceCarrier.charAt(0) != '/' || invoiceCarrier.trim().length() != 8){
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
        if(invoiceMethod == 2){
            if (order.getInvoiceCarrier() != null || order.getInvoiceVat() != null){
                orderDto.setMessage("一般會員載具不需輸入載具號碼，且不可輸入統編");
                orderDto.setSuccessful(false);
                return orderDto;
            }
        }
        if(invoiceMethod == 3) {  // 統編
            if (invoiceCarrier != null || order.getInvoiceVat() == null){
                orderDto.setMessage("統編發票不需輸入載具號碼，且需輸入統編");
                orderDto.setSuccessful(false);
                return orderDto;
            }
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
        // 會員使用優惠券
        Integer customerCouponsId = order.getCustomerCouponsId();
        if(customerCouponsId != null) {
            CustomerCoupons customerCoupon = customerCouponDao.findByCustomerIdAndCustomerCouponsId(customerId, customerCouponsId);
            if (customerCoupon == null || customerCoupon.getCouponQuantity() <= 0) {
                orderDto.setMessage("無此優惠券或數量不足");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            if(order.getCouponDiscount() < 0 || (!order.getCouponDiscount().equals(customerCoupon.getCoupon().getDiscount()))){
                orderDto.setMessage("優惠券折抵金額錯誤");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            customerCouponService.updateCouponQuantity(customerId, customerCoupon.getCouponId() , customerCoupon.getCouponQuantity() - 1);
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
            if(order.getLoyaltyDiscount() < 0 || ((customerLoyaltyCard.getPoints()) - order.getLoyaltyDiscount() < 0)){
                orderDto.setMessage("集點卡折抵金額錯誤");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            // 折抵集點卡點數
            memberLoyaltyCardService.UpdatePoints(loyaltyCardId, order.getLoyaltyDiscount());
        }

        // 會員使用點數
        if(order.getCustomerMoneyDiscount() < 0 || order.getCustomerMoneyDiscount() > member.getCustomerMoney()){
            orderDto.setMessage("會員錢包折抵金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        memberService.updateMemberMoneyById(customerId, (- order.getCustomerMoneyDiscount()));

        order.setOrderStatus(1);
        order.setOrderCreateDatetime(new Timestamp(System.currentTimeMillis()));
        orderDao.insert(order);
        // 存商品
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderId(order.getOrderId());
            orderDetailDao.insert(orderDetail);
        }

        // 傳送發票參數給綠界
        String addInvoiceNo = invoiceService.createInvoice(order);
        if(isEmpty(addInvoiceNo)){
            orderDto.setMessage("開立發票失敗");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        // 存發票
        orderDao.saveInvoiceNo(order.getOrderId(), addInvoiceNo);

        orderDto.setMessage("訂單新增成功");
        orderDto.setSuccessful(true);
        // 訂單新增時 增加集點卡點數
        memberLoyaltyCardService.updateMemberStoreLoyaltyPoints(storeId, customerId, order.getProductAmount());
        // 建立通知的內容 依商店ID 發送訂單新增訊息
        notificationService.addOrderNotify(order.getOrderId(), order.getOrderCreateDatetime(), storeId);
        return orderDto;
    }
//---------------------------------------------------------------------------------------
    // todo 未付款訂單
    public OrderDto addNewOrder(Orders order, List<OrderDetail> orderDetails) {
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
        if(order.getPaymentMethod() != 1 || isEmpty(order.getPaymentAmount()) || order.getPaymentAmount() < 0){
            orderDto.setMessage("付款方法或付款金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        // 判斷發票
        Integer invoiceMethod = order.getInvoiceMethod();
        String invoiceCarrier = order.getInvoiceCarrier();
        String invoiceVat = order.getInvoiceVat() + "";
        if(invoiceMethod != 1 && invoiceMethod != 2 && invoiceMethod != 3){
            orderDto.setMessage("發票方式錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        if(invoiceMethod == 1){  // 手機載具
            if (order.getInvoiceCarrier() == null ||  order.getInvoiceVat() != null){
                orderDto.setMessage("手機載具需輸入載具號碼，且不可輸入統編");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            if(invoiceCarrier.charAt(0) != '/' || invoiceCarrier.trim().length() != 8){
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
        if(invoiceMethod == 2){
            if (order.getInvoiceCarrier() != null || order.getInvoiceVat() != null){
                orderDto.setMessage("一般會員載具不需輸入載具號碼，且不可輸入統編");
                orderDto.setSuccessful(false);
                return orderDto;
            }
        }
        if(invoiceMethod == 3) {  // 統編
            if (invoiceCarrier != null || order.getInvoiceVat() == null){
                orderDto.setMessage("統編發票不需輸入載具號碼，且需輸入統編");
                orderDto.setSuccessful(false);
                return orderDto;
            }
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
        // 會員使用優惠券
        Integer customerCouponsId = order.getCustomerCouponsId();
        if(customerCouponsId != null) {
            CustomerCoupons customerCoupon = customerCouponDao.findByCustomerIdAndCustomerCouponsId(customerId, customerCouponsId);
            if (customerCoupon == null || customerCoupon.getCouponQuantity() <= 0) {
                orderDto.setMessage("無此優惠券或數量不足");
                orderDto.setSuccessful(false);
                return orderDto;
            }
            if(order.getCouponDiscount() < 0 || (!order.getCouponDiscount().equals(customerCoupon.getCoupon().getDiscount()))){
                orderDto.setMessage("優惠券折抵金額錯誤");
                orderDto.setSuccessful(false);
                return orderDto;
            }
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
            if(order.getLoyaltyDiscount() < 0 || ((customerLoyaltyCard.getPoints()) - order.getLoyaltyDiscount() < 0)){
                orderDto.setMessage("集點卡折抵金額錯誤");
                orderDto.setSuccessful(false);
                return orderDto;
            }
        }

        // 會員使用點數
        if(order.getCustomerMoneyDiscount() < 0 || order.getCustomerMoneyDiscount() > member.getCustomerMoney()){
            orderDto.setMessage("會員錢包折抵金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }

        order.setOrderStatus(0);
        order.setOrderCreateDatetime(new Timestamp(System.currentTimeMillis()));
        orderDao.insert(order);
        // 存商品
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderId(order.getOrderId());
            orderDetailDao.insert(orderDetail);
        }

        orderDto.setMessage("訂單新增成功");
        orderDto.setSuccessful(true);
        return orderDto;
    }

    // TODO 付款成功
    public Orders paySuccessOrder(Orders order) {

        Orders newOrder = new Orders();
        int customerId = order.getCustomerId();
        int storeId = order.getStoreId();
        if(!(order.getOrderStatus() == 0)){
            newOrder.setMessage("訂單狀態錯誤");
            newOrder.setSuccessful(false);
            return newOrder;
        }
        // 傳送發票參數給綠界
        String addInvoiceNo = invoiceService.createInvoice(order);
        if(isEmpty(addInvoiceNo)){
            newOrder.setMessage("開立發票失敗");
            newOrder.setSuccessful(false);
            return newOrder;
        }
        // 存發票
        orderDao.saveInvoiceNo(order.getOrderId(), addInvoiceNo);

        // 會員使用優惠券折抵
        Integer customerCouponsId = order.getCustomerCouponsId();
        if(customerCouponsId != null) {
            CustomerCoupons customerCoupon = customerCouponDao.findByCustomerIdAndCustomerCouponsId(customerId, customerCouponsId);
            customerCouponService.updateCouponQuantity(customerId, customerCoupon.getCouponId() , customerCoupon.getCouponQuantity() - 1);
        }
        // 會員使用集點卡折抵
        Integer loyaltyCardId = order.getLoyaltyCardId();
        if(loyaltyCardId != null) {
            // 折抵集點卡點數
            memberLoyaltyCardService.UpdatePoints(loyaltyCardId, order.getLoyaltyDiscount());
        }
        // 會員使用點數折抵
        memberService.updateMemberMoneyById(customerId, (- order.getCustomerMoneyDiscount()));
        order.setOrderStatus(1);
        orderDao.update(order);

        newOrder.setMessage("訂單付款成功");
        newOrder.setSuccessful(true);
        // 訂單付款成功 增加集點卡點數
        memberLoyaltyCardService.updateMemberStoreLoyaltyPoints(storeId, customerId, order.getProductAmount());
        // 建立通知的內容 依商店ID 發送訂單新增訊息
        notificationService.addOrderNotify(order.getOrderId(), order.getOrderCreateDatetime(), storeId);
        return newOrder;
    }
//---------------------------------------------------------------------------------------
    // 前台 訂單列表 顯示 全訂單
    @Override
    public Page<OrderDto> findByCustomerId(Integer customerId, Pageable pageable) {
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
    // 前台 訂單列表 顯示 篩選日期
    @Override
    public Page<OrderDto> findByCustomerIdAndDateRange(Integer customerId, Timestamp dateStart, Timestamp dateEnd, Pageable pageable) {
        Page<Orders> orders;
        // 根據 customerId 和訂單建立日期範圍來篩選訂單
        orders = orderRepository.findByCustomerIdAndOrderCreateDatetimeBetween(customerId, dateStart, dateEnd, pageable);

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
    public OrderDto findByMemberOrderId(Integer orderId){
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
        final Integer ordersStar = newOrder.getOrderScore();
        final String orderFeedBack = newOrder.getOrderFeedback();
        final Integer storeId = oldOrder.getStoreId();
        if(!(isEmpty(oldOrder.getOrderScore()))){
            newOrder.setMessage("不可重複評分");
            newOrder.setSuccessful(false);
            return newOrder;
        }
        if( ordersStar < 1 || ordersStar > 5 ){
            newOrder.setMessage("未輸入評分");
            newOrder.setSuccessful(false);
            return newOrder;
        }
        if (isEmpty(storeId)) {
            newOrder.setMessage("無此商店");
            newOrder.setSuccessful(false);
            return newOrder;
        }
        oldOrder.setOrderScore(ordersStar);
        oldOrder.setOrderFeedback(orderFeedBack);
        orderDao.update(oldOrder);
        // 放入商店評分內
        storeService.updateStoreRank(storeId, Float.valueOf(ordersStar));
        newOrder.setMessage("評分完成");
        newOrder.setSuccessful(true);
        return newOrder;
    }

    // 後台 訂單列表 顯示 & 依條件查詢
    @Override
    public Page<Orders> findByCriteria(
            Integer orderId, Integer storeId, String storeName, String memberNickname,
            Integer orderStatus, LocalDate dateStart, LocalDate dateEnd, Integer page, Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "orderCreateDatetime"));

        // 檢查 查詢日起迄 是否為 null
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;
        if (dateStart != null) {
            startTimestamp = Timestamp.valueOf(dateStart.atStartOfDay());
        }
        if (dateEnd != null) {
            endTimestamp = Timestamp.valueOf(dateEnd.plusDays(1).atStartOfDay());
        }

        return orderRepository.findByCriteria(
                orderId, storeId, storeName, memberNickname,
                orderStatus, startTimestamp, endTimestamp, pageable
        );
    }

    // 後台 訂單明細 顯示
    @Override
    public List<OrderDetail> findByOrderId(Integer orderId) {
        return orderDetailDao.selectByOrderId(orderId);
    }

    // 後台 訂單明細 修改狀態
    @Override
    public Orders updateStatus(Orders newOrder) {
        final Orders oldOrder = orderDao.selectByOrderId(newOrder.getOrderId());
        // 舊訂單狀態值 需 < 新訂單狀態值
        if(oldOrder.getOrderStatus() >= newOrder.getOrderStatus() ){
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