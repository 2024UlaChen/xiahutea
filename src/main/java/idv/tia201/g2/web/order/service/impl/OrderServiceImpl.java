package idv.tia201.g2.web.order.service.impl;

import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.dao.impl.MemberDaoImpl;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.dao.OrderDetailDao;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.util.OrderMappingUtil;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import idv.tia201.g2.web.product.dao.ProductDao;
import idv.tia201.g2.web.product.vo.Product;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.util.VatUtil;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    private OrderMappingUtil orderMappingUtil;

    // todo
    // 發票api

    // TODO
    // 前台 訂單新增
    @Override
    public OrderDto addOrder(Orders newOrder, List<OrderDetail> orderDetails) {
        OrderDto orderDto = new OrderDto();

        int customerId = newOrder.getCustomerId();
        Member member = memberDao.findMemberById(customerId);
        if(member == null) {
            orderDto.setMessage("無此會員ID");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        int storeId = newOrder.getStoreId();
        Store store = storeDao.findByStoreId(storeId);
        if(store == null) {
            orderDto.setMessage("無此商店ID");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        if(newOrder.getCouponDiscount() < 0 || newOrder.getLoyaltyDiscount() < 0 || newOrder.getCustomerMoneyDiscount() < 0){
            orderDto.setMessage("折抵金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        if(newOrder.getOrderProductQuantity() < 0 || newOrder.getProductAmount() < 0 ){
            orderDto.setMessage("商品數量或商品總金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        if(newOrder.getPaymentMethod() != 1 || isEmpty(newOrder.getPaymentAmount())){
            orderDto.setMessage("付款方法或付款金額錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        if(newOrder.getInvoiceMethod() != 1 && newOrder.getInvoiceMethod() != 2 && newOrder.getInvoiceMethod() != 3){
            orderDto.setMessage("發票方式錯誤");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        final String invoiceCarrier = newOrder.getInvoiceCarrier();
        final String invoiceVat = newOrder.getInvoiceVat() + "";
        if(newOrder.getInvoiceMethod() == 2){  // 載具
            if(invoiceCarrier.charAt(0) != '/' || invoiceCarrier.trim().length() != 8){
                orderDto.setMessage("載具輸入錯誤");
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
        if (newOrder.getInvoiceMethod() == 3) {  // 統編
            if(!(VatUtil.isValidTWBID(invoiceVat))){
                orderDto.setMessage("統編輸入錯誤");
                orderDto.setSuccessful(false);
                return orderDto;
            }
        }
        if( isEmpty(newOrder.getReceiverMethod()) || isEmpty(newOrder.getReceiverName()) || isEmpty(newOrder.getReceiverPhone()) || isEmpty(newOrder.getReceiverDatetime())){
            orderDto.setMessage("未輸入取貨資訊");
            orderDto.setSuccessful(false);
            return orderDto;
        }
        // 訂單明細檢查
        for (OrderDetail orderDetail : orderDetails) {
            // todo 檢查商品id
            int productId = orderDetail.getProductId();
             //  Product product = productDao.findById(productId);

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


        newOrder.setOrderStatus(1);
        newOrder.setCustomerId(customerId);
        newOrder.setCustomerCouponsId(newOrder.getCustomerCouponsId());
        newOrder.setCouponDiscount(newOrder.getCouponDiscount());
        newOrder.setLoyaltyCardId(newOrder.getLoyaltyCardId());
        newOrder.setLoyaltyDiscount(newOrder.getLoyaltyDiscount());
        newOrder.setOrderProductQuantity(newOrder.getOrderProductQuantity());
        newOrder.setPaymentAmount(newOrder.getPaymentAmount());
        newOrder.setProcessingFees(10);
        newOrder.setPaymentMethod(newOrder.getPaymentMethod());
        newOrder.setPaymentAmount(newOrder.getPaymentAmount());
        newOrder.setInvoiceMethod(newOrder.getInvoiceMethod());
        newOrder.setInvoiceVat(newOrder.getInvoiceVat());
        newOrder.setInvoiceCarrier(newOrder.getInvoiceCarrier());
        newOrder.setReceiverMethod(newOrder.getReceiverMethod());
        newOrder.setReceiverName(newOrder.getReceiverName());
        newOrder.setReceiverPhone(newOrder.getReceiverPhone());
        newOrder.setReceiverAddress(newOrder.getReceiverAddress());
        newOrder.setReceiverDatetime(newOrder.getReceiverDatetime());
        newOrder.setOrderNote(newOrder.getOrderNote());
//        newOrder.setOrderCreateDatetime((System.currentTimeMillis()));
        orderDao.insert(newOrder);




        for (OrderDetail orderDetail : orderDetails) {
            orderDetailDao.insert(orderDetail);
        }
        orderDto.setMessage("訂單已成立");
        orderDto.setSuccessful(true);
        orderDto.setOrders(newOrder);
        orderDto.setOrderDetails(orderDetails);
        return orderDto;
    }

    // -------- FINISH ---------------------------------
    // 前台 訂單列表 顯示
    @Override
    public List<OrderDto> findByCustomerId(int customerId) {
        List<Orders> orders = orderDao.selectBycCustomerId(customerId);
        // 建立流
        Stream<Orders> stream = orders.stream();
        // 映射每個訂單
        Stream<OrderDto> orderDtoStream = stream.map(order -> {
            DisputeOrder disputeOrder = disputeDao.selectByOrderId(order.getOrderId());
            List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(order.getOrderId());
            return orderMappingUtil.createOrderDto(order, disputeOrder, orderDetails);
        });
        // 收集結果並返回
        return orderDtoStream.collect(Collectors.toList());
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
    public List<Orders> findAll() {
        return orderDao.selectAll();
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