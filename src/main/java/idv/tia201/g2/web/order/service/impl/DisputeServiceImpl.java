package idv.tia201.g2.web.order.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import idv.tia201.g2.web.member.dao.MemberDao;
import idv.tia201.g2.web.member.service.MemberService;
import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.order.dao.DisputeDao;
import idv.tia201.g2.web.order.dao.DisputeRepository;
import idv.tia201.g2.web.order.dao.OrderDao;
import idv.tia201.g2.web.order.dao.OrderDetailDao;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.DisputeService;
import idv.tia201.g2.web.order.service.NotificationService;
import idv.tia201.g2.web.order.service.OrderService;
import idv.tia201.g2.web.order.util.OrderMappingUtil;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.order.vo.OrderDetail;
import idv.tia201.g2.web.order.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Transactional
public class DisputeServiceImpl implements DisputeService {

    @Autowired
    private DisputeDao disputeDao;
    @Autowired
    private DisputeRepository disputeRepository;
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
    @Autowired
    private NotificationService notificationService;
//    @Autowired
//    private OrderService orderService;

    // -------- FINISH ---------------------------------
    // 前台 爭議表格 顯示
    @Override
    public OrderDto findByOrderId(Integer orderId) {
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
        Member member = memberDao.findMemberById(disputeOrder.getCustomerId());
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

        // 發送爭議通知
        Orders order = orderDao.selectByOrderId(disputeOrder.getOrderId());
        notificationService.addDisputeNotify(disputeOrder.getDisputeOrderId(), disputeOrder.getApplyDatetime());
        return disputeOrder;
    }

    // 後台 爭議列表 顯示 & 依條件查詢
    @Override
    public Page<DisputeOrder> findByCriteria(
            Integer disputeOrderId, Integer orderId, Integer storeId, String storeName, String memberNickname,
            Integer disputeStatus, LocalDate dateStart, LocalDate dateEnd, Integer page, Integer size
    ) {
        // 分頁排序
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "applyDatetime"));

        // 檢查 查詢起迄日期 是否null
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;
        if (dateStart != null) {
            startTimestamp = Timestamp.valueOf(dateStart.atStartOfDay());
        }
        if (dateEnd != null) {
            endTimestamp = Timestamp.valueOf(dateEnd.plusDays(1).atStartOfDay());
        }

        return disputeRepository.findByCriteria(
                disputeOrderId, orderId, storeId, storeName, memberNickname,
                disputeStatus, startTimestamp, endTimestamp, pageable
        );
    }

    // 後台 爭議明細 顯示
    @Override
    public OrderDto findByDisputeOrderId(Integer disputeOrderId) {
        DisputeOrder disputeOrder = disputeDao.selectByDisputeId(disputeOrderId);
        if(disputeOrder == null) {
            return null;
        }
        Orders order = orderDao.selectByOrderId(disputeOrder.getOrderId());
        List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(disputeOrder.getOrderId());
        return orderMappingUtil.createOrderDto(order, disputeOrder, orderDetails);
    }

    // 後台 爭議明細 修改 > 爭議狀態
    @Override
    public DisputeOrder updateInfo(DisputeOrder newDispute) {
        final DisputeOrder oldDispute = disputeDao.selectByDisputeId(newDispute.getDisputeOrderId());
        // 爭議狀態：可修改的狀態只有1
        if(oldDispute.getDisputeStatus() == 2 || oldDispute.getDisputeStatus() == 3) {
            newDispute.setMessage("修改失敗，爭議訂單已處理完成");
            newDispute.setSuccessful(false);
            return newDispute;
        }
        // 爭議狀態：同意
        if(newDispute.getDisputeStatus() == 2){
            if(isEmpty(newDispute.getRefundAmount()) || !(isEmpty(newDispute.getRejectReason()))) {
                newDispute.setMessage("修改失敗，必填欄位需完成");
                newDispute.setSuccessful(false);
                return newDispute;
            }
            Orders order = orderDao.selectByOrderId(newDispute.getOrderId());
            if(newDispute.getRefundAmount() > (order.getProductAmount() + order.getProcessingFees())){
                newDispute.setMessage("修改失敗，退款金額不可大於商品金額+系統費");
                newDispute.setSuccessful(false);
                return newDispute;
            }
            // 爭議同意： 退款至會員錢包
            Member member = memberDao.findMemberById(oldDispute.getCustomerId()); // 查找會員
            if(member == null) {
                newDispute.setMessage("申請失敗，無此會員ID");
                newDispute.setSuccessful(false);
                return newDispute;
            }
            // 會員錢包增加 退款金額
            memberService.updateMemberMoneyById(member.getCustomerId(), newDispute.getRefundAmount());
        }
        // 爭議狀態：不同意
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

        newDispute.setMessage("修改成功");
        newDispute.setSuccessful(true);
        return newDispute;
    }
}