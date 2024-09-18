package idv.tia201.g2.web.order.service.impl;

import java.sql.Timestamp;
import java.util.List;
import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.dao.OrderDetailDao;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.DisputeService;
import idv.tia201.g2.web.order.util.OrderMappingUtil;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Transactional
public class DisputeServiceImpl implements DisputeService {

    @Autowired
    private DisputeDao disputeDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMappingUtil orderMappingUtil;

    // FINISH
    // 後台 爭議列表
    @Override
    public List<DisputeOrder> findAll() {
        return disputeDao.selectAll();
    }

    // 後台 爭議明細
    @Override
    public OrderDto findByDisputeOrderId(int disputeOrderId) {
        DisputeOrder disputeOrder = disputeDao.selectByDisputeId(disputeOrderId);
        if(disputeOrder == null) {
            return null;
        }
        Orders order = orderDao.selectByOrderId(disputeOrder.getOrderId());
        List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(disputeOrder.getOrderId());

        return orderMappingUtil.createOrderDto(order, disputeOrder, orderDetails);
    }

    // 前台 爭議申請 顯示
    public OrderDto findByOrderId(int orderId) {
        Orders order = orderDao.selectByOrderId(orderId);
        List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(orderId);
        DisputeOrder disputeOrder = disputeDao.selectByOrderId(orderId);
        if(order == null) {
            return null;
        }
        return orderMappingUtil.createOrderDto(order, disputeOrder, orderDetails);
    }

    // 前台 爭議申請 新增
    @Override
    public DisputeOrder add(DisputeOrder disputeOrder) {
        if(!(isEmpty(disputeOrder.getDisputeOrderId()))) {
            disputeOrder.setMessage("申請失敗，已有爭議資料");
            disputeOrder.setSuccessful(false);
            return disputeOrder;
        }
        if(isEmpty(disputeOrder.getOrderId())) {
            disputeOrder.setMessage("申請失敗，無訂單編號");
            disputeOrder.setSuccessful(false);
            return disputeOrder;
        }
        if(isEmpty(disputeOrder.getDisputeReason())) {
            disputeOrder.setMessage("申請失敗，無爭議原因");
            disputeOrder.setSuccessful(false);
            return disputeOrder;
        }
        disputeOrder.setOrderId(disputeOrder.getOrderId());
        disputeOrder.setDisputeStatus(1);
        disputeOrder.setDisputeReason(disputeOrder.getDisputeReason());
        disputeOrder.setApplyDatetime(new Timestamp(System.currentTimeMillis()));
        disputeDao.insert(disputeOrder);
        disputeOrder.setMessage("申請完成");
        disputeOrder.setSuccessful(true);
        return disputeOrder;
    }

    //------------------------------------------------------


    // todo
    @Override
    public DisputeOrder updateInfo(DisputeOrder newDispute) {
        final DisputeOrder oldDispute = disputeDao.selectByDisputeId(newDispute.getDisputeOrderId());
        // 同意時
        if(newDispute.getDisputeStatus() == 2){
            if(isEmpty(newDispute.getRefundAmount()) || !(isEmpty(newDispute.getRejectReason()))) {
                newDispute.setMessage("修改失敗");
                newDispute.setSuccessful(false);
                System.out.println(newDispute.getRefundAmount()+", " +isEmpty(newDispute.getRefundAmount()) );
                System.out.println(newDispute.getRejectReason() +", "+ isEmpty(newDispute.getRejectReason()));
                System.err.println("錯誤: refundAmount 或 rejectReason 錯誤");
                return newDispute;
            }
        }
        if(newDispute.getDisputeStatus() == 3){
            if(!(isEmpty(newDispute.getRefundAmount())) || isEmpty(newDispute.getRejectReason())){
                newDispute.setMessage("修改失敗");
                newDispute.setSuccessful(false);
                System.err.println("錯誤: refundAmount 或 rejectReason 錯誤");
                return newDispute;
            }
        }
//        oldDispute.setOrderId(oldDispute.getOrderId());
//        oldDispute.setCustomerId(oldDispute.getCustomerId());
        oldDispute.setRefundAmount(newDispute.getRefundAmount());
        oldDispute.setRejectReason(newDispute.getRejectReason());
        oldDispute.setDisputeNotes(newDispute.getDisputeNotes());
        oldDispute.setDisputeStatus(newDispute.getDisputeStatus());
        disputeDao.update(newDispute);
        newDispute.setMessage("修改成功");
        newDispute.setSuccessful(true);
        return newDispute;
    }




}
