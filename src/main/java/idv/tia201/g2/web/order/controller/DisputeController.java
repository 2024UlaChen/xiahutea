package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.DisputeService;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dispute")
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    // FINISH
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

    // ------------------------------------------------------
    // todo
    // 後台 爭議明細 修改
    @PutMapping({"manage/{disputeOrderId}"})
    public DisputeOrder save(
            @PathVariable Integer disputeOrderId,
            @RequestBody DisputeOrder reqDisputeOrder
    ){
        reqDisputeOrder.setDisputeOrderId(disputeOrderId);
        return disputeService.updateInfo(reqDisputeOrder);
    }

    // todo 新增資料

}
