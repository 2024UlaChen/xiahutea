package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.DisputeService;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("dispute")
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    // -------- FINISH ---------------------------------
    // 前台 爭議申請表 顯示
    @GetMapping("member/applyDispute/{orderId}")
    public OrderDto apply(@PathVariable Integer orderId){
        return disputeService.findByOrderId(orderId);
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
    public List<DisputeOrder> manage(){
        return disputeService.findAll();
    }

    // 後台 爭議明細 顯示
    @GetMapping({"manage/{disputeOrderId}"})
    public OrderDto detail(@PathVariable Integer disputeOrderId){
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