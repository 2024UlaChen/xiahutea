package idv.tia201.g2.web.order.service.impl;

import java.sql.Timestamp;
import java.util.List;
import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
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
    private MemberDao memberDao;

    @Autowired
    private OrderMappingUtil orderMappingUtil;
    @Autowired
    private MemberService memberService;

    // todo 退款錢包
    // -------- FINISH ---------------------------------
    // 前台 爭議表格 顯示
    @Override
    public OrderDto findByOrderId(int orderId) {
        Orders order = orderDao.selectByOrderId(orderId);
        List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(orderId);
        DisputeOrder disputeOrder = disputeDao.selectByOrderId(orderId);
        if(order == null) {
            return null;
        }
        return orderMappingUtil.createOrderDto(order, disputeOrder, orderDetails);
    }

    // 前台 爭議表格 申請
    @Override
    public DisputeOrder add(DisputeOrder disputeOrder) {
        Member member = memberDao.findMemberById(disputeOrder.getCustomerId()); // 查找會員
        if(member == null) {
            disputeOrder.setMessage("申請失敗，無此會員ID");
            disputeOrder.setSuccessful(false);
            return disputeOrder;
        }
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
        disputeOrder.setDisputeStatus(1);
        disputeOrder.setApplyDatetime(new Timestamp(System.currentTimeMillis()));
        disputeDao.insert(disputeOrder);
        disputeOrder.setMessage("申請完成");
        disputeOrder.setSuccessful(true);
        return disputeOrder;
    }

    // 後台 爭議列表 顯示
    @Override
    public List<DisputeOrder> findAll() {
        return disputeDao.selectAll();
    }

    // 後台 爭議明細 顯示
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

    // 後台 爭議明細 修改
    @Override
    public DisputeOrder updateInfo(DisputeOrder newDispute) {
        final DisputeOrder oldDispute = disputeDao.selectByDisputeId(newDispute.getDisputeOrderId());
        if(oldDispute.getDisputeStatus() == 2 || oldDispute.getDisputeStatus() == 3) {
            newDispute.setMessage("修改失敗，爭議訂單已處理完成");
            newDispute.setSuccessful(false);
            return newDispute;
        }
        // 同意時
        if(newDispute.getDisputeStatus() == 2){
            if(isEmpty(newDispute.getRefundAmount()) || !(isEmpty(newDispute.getRejectReason()))) {
                newDispute.setMessage("修改失敗，必填欄位需完成");
                newDispute.setSuccessful(false);
                return newDispute;
            }
        }
        if(newDispute.getDisputeStatus() == 3){
            if(!(isEmpty(newDispute.getRefundAmount())) || isEmpty(newDispute.getRejectReason())){
                newDispute.setMessage("修改失敗，必填欄位需完成");
                newDispute.setSuccessful(false);
                return newDispute;
            }
        }
        oldDispute.setRefundAmount(newDispute.getRefundAmount());
        oldDispute.setRejectReason(newDispute.getRejectReason());
        oldDispute.setDisputeNotes(newDispute.getDisputeNotes());
        oldDispute.setDisputeStatus(newDispute.getDisputeStatus());
        oldDispute.setUpdateDatetime(new Timestamp(System.currentTimeMillis()));
        disputeDao.update(oldDispute);

        // 爭議同意： 退款至會員錢包
        if(newDispute.getDisputeStatus() == 2){
            Member member = memberDao.findMemberById(oldDispute.getCustomerId()); // 查找會員
            if(member == null) {
                newDispute.setMessage("申請失敗，無此會員ID");
                newDispute.setSuccessful(false);
                return newDispute;
            }
            // 增加 退款金額
            memberService.updateMemberMoneyById(member.getCustomerId(), newDispute.getRefundAmount());
        }
        newDispute.setMessage("修改成功");
        newDispute.setSuccessful(true);
        return newDispute;
    }
}