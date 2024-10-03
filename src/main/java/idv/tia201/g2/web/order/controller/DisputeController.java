package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.DisputeService;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@RestController
@RequestMapping("dispute")
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    // -------- FINISH ---------------------------------
    // 前台 爭議申請表 顯示
    @GetMapping("member/applyDispute/{orderId}")
    public OrderDto apply(@PathVariable Integer orderId, HttpSession httpSession){
        TotalUserDTO totalUserDTO = (TotalUserDTO)  httpSession.getAttribute("totalUserDTO");
        OrderDto dispute = disputeService.findByOrderId(orderId);
        if (totalUserDTO.getUserTypeId() != 0 || !(totalUserDTO.getUserId().equals(dispute.getOrders().getCustomerId()))) {
            OrderDto orderDto = new OrderDto();
            return orderDto;
        }
        return dispute;
    }

    // 前台 爭議申請表 新增
    @PostMapping("member/applyDispute/{orderId}")
    public DisputeOrder addDispute(
            @PathVariable Integer orderId,
            @RequestBody DisputeOrder reqDisputeOrder){
        reqDisputeOrder.setOrderId(orderId);
        return disputeService.add(reqDisputeOrder);
    }

    // 後台 爭議列表 顯示
    @GetMapping("manage")
    public Page<DisputeOrder> findAllByPageable(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer disputeOrderId,
            @RequestParam(required = false) Integer orderId,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String memberNickname,
            @RequestParam(required = false) Integer disputeStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateStart, // 開始日期
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate dateEnd, // 結束日期
            HttpSession httpSession
    ){
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "applyDatetime"));
        // 如果不是店家或管理者權限 不可檢視
        if (totalUserDTO.getUserTypeId() != 3 && totalUserDTO.getUserTypeId() != 1) {
            return Page.empty();
        }

        // 檢查 查詢日起迄 是否為 null
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;
        if (dateStart != null) {
            startTimestamp = Timestamp.valueOf(dateStart.atStartOfDay());
        }
        if (dateEnd != null) {
            endTimestamp = Timestamp.valueOf(dateEnd.plusDays(1).atStartOfDay());
        }
        // 判斷user的權限，如果是admin可看全部訂單，如果是store只能看storeId的訂單
        Integer storeId = (totalUserDTO.getUserTypeId() == 1) ? totalUserDTO.getUserTypeId() : null;
        return disputeService.findByCriteria(disputeOrderId, orderId, storeId, storeName, memberNickname, disputeStatus, startTimestamp, endTimestamp, pageable);
    }

    // 後台 爭議明細 顯示
    @GetMapping({"manage/{disputeOrderId}"})
    public OrderDto detail(@PathVariable Integer disputeOrderId, HttpSession httpSession){
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        if(totalUserDTO.getUserTypeId() != 3){
            return null;
        }
        return disputeService.findByDisputeOrderId(disputeOrderId);
    }

    // 後台 爭議明細 修改
    @PutMapping({"manage/{disputeOrderId}"})
    public DisputeOrder save(
            @PathVariable Integer disputeOrderId,
            @RequestBody DisputeOrder reqDisputeOrder
    ){
        reqDisputeOrder.setDisputeOrderId(disputeOrderId);
        return disputeService.updateInfo(reqDisputeOrder);
    }
}