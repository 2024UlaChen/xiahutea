package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.service.DisputeService;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Repository
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    @GetMapping
    public List<DisputeOrder> manage(){
        return disputeService.findAll();
    }

    @GetMapping({"disputeOrderId"})
    public DisputeOrder detail(@PathVariable Integer disputeOrderId){
        return disputeService.findByDisputeId(disputeOrderId);
    }

    // todo 修改資料

    // todo 新增資料

}
