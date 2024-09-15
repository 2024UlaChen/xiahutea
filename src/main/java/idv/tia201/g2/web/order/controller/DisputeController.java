package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.service.DisputeService;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dispute")
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    // 後台 爭議列表顯示
    @GetMapping("manage")
    public List<DisputeOrder> manage(){
        return disputeService.findAll();
    }

    //後台 爭議明細顯示
    @GetMapping({"manage/{disputeOrderId}"})
    public OrderDto detail(@PathVariable Integer disputeOrderId){
        return disputeService.findByDisputeOrderId(disputeOrderId);
    }

    // todo 修改資料

    // todo 新增資料

}
