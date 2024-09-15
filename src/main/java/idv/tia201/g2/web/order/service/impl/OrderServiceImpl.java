package idv.tia201.g2.web.order.service.impl;

import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.dao.OrderDetailDao;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.util.OrderMappingUtil;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
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
    private OrderMappingUtil orderMappingUtil;


    private OrderDto orderDto ;


    // todo
    // 發票api

    // 後台 訂單查詢
    @Override
    public List<Orders> findAll() {
        return orderDao.selectAll();
    }
    // 後台 訂單明細
    @Override
    public List<OrderDetail> findByOrderId(int orderId) {
        return orderDetailDao.selectByOrderId(orderId);
    }

//    public OrderDto findByOrderId(int orderId) {
//        return orderMappingUtil.mapToOrderDto(
//                orderDao.selectByOrderId(orderId),
//                orderDetailDao.selectByOrderId(orderId),
//                disputeDao.selectByOrderId(orderId)
//        );
//    }

    // 前台 訂單列表
    @Override
    public List<Object[]> findByCustomerId(int customerId) {
        return orderDao.selectBycCustomerId(customerId);
    }

    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        return null;
    }

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



    // 前台操作邏輯
    @Override
    public Orders addOrder(Orders order) {
        if(isEmpty(order.getReceiverAddress()) && isEmpty(order.getReceiverDatetime())){
            order.setReceiverAddress("未輸入取貨資訊");
            order.setSuccessful(false);
            return order;
        }
        if(isEmpty(order.getReceiverName()) || isEmpty(order.getReceiverPhone())){
            order.setReceiverAddress("未輸入聯絡資訊");
            order.setSuccessful(false);
            return order;
        }
        if(isEmpty(order.getInvoiceMethod())){
            order.setReceiverAddress("未選擇發票方式");
            order.setSuccessful(false);
            return order;
        }

        final String invoiceCarrier = order.getInvoiceCarrier();
        final String invoiceVat = order.getInvoiceVat() + "";
        if(order.getInvoiceMethod() == 2){  // 載具
            if(invoiceCarrier.charAt(0) != '/' || invoiceCarrier.trim().length() != 8){
                order.setMessage("載具輸入錯誤");
                order.setSuccessful(false);
                return order;
            }
            for(int i = 1; i < invoiceCarrier.length(); i++){
                char CarrierChar = invoiceCarrier.charAt(i);
                boolean isMark = (CarrierChar == 43 || CarrierChar == 45 || CarrierChar == 46);
                boolean isNum = (CarrierChar>=48 && CarrierChar<=57);
                boolean isEng = (CarrierChar>=65 && CarrierChar<=90);
                if(!(isMark || isNum || isEng)){
                    order.setMessage("載具輸入錯誤");
                    order.setSuccessful(false);
                    return order;
                }
            }
        }
        if (order.getInvoiceMethod() == 3) {  // 統編
            if(invoiceVat.trim().length() != 8){
                order.setMessage("統編輸入錯誤");
                order.setSuccessful(false);
                return order;
            }
            for(int i = 0; i < invoiceVat.length(); i++){
                char vatChar = invoiceVat.charAt(i);
                if(!(vatChar>=48 && vatChar<=57)){
                    order.setMessage("統編輸入錯誤");
                    order.setSuccessful(false);
                    return order;
                }
            }
        }
//        if(orderDao.insert(order)){
//            order.setMessage("訂單成立失敗");
//            order.setSuccessful(false);
//            return order;
//        }
        order.setMessage("訂單已成立");
        order.setSuccessful(true);
        return order;
    }

    public Orders addStar(Orders order){
        final Orders oOrder = orderDao.selectByOrderId(order.getOrderId());
        final int ordersStar = order.getOrderScore();
        final String orderFeedBack = order.getOrderFeedback();

        if(ordersStar == 0){
            order.setMessage("未輸入評分");
            order.setSuccessful(false);
            return order;
        }
        if((orderDao.selectByOrderId(order.getOrderId())).getOrderScore() != null ){
            order.setMessage("不可重複評分");
            order.setSuccessful(false);
            return order;
        }

        oOrder.setOrderScore(ordersStar);
        oOrder.setOrderFeedback(orderFeedBack);
        order.setMessage("評分完成");
        order.setSuccessful(true);
        return order;
    }




}
