package idv.tia201.g2.web.order.service;

import idv.tia201.g2.web.order.vo.DisputeOrder;
import java.util.List;

public interface DisputeService {

    DisputeOrder add(DisputeOrder disputeOrder);
    DisputeOrder update(DisputeOrder disputeOrder);
    List<DisputeOrder> findAll();
}
