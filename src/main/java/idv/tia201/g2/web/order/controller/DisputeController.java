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
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession httpSession
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "applyDatetime"));
        return disputeService.findAll(pageable);
    }


    // 後台 爭議明細 顯示
    @GetMapping({"manage/getUser"})
    public TotalUserDTO getUser(HttpSession httpSession){
        TotalUserDTO totalUserDTO = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        if(totalUserDTO.getUserTypeId() != 3){
            return null;
        }
        return totalUserDTO;
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