package idv.tia201.g2.web.order.service;

import java.time.LocalDate;
import idv.tia201.g2.web.order.dto.OrderDto;
import idv.tia201.g2.web.order.vo.DisputeOrder;
import org.springframework.data.domain.Page;

public interface DisputeService  {

    // 前台 爭議表格 顯示
    OrderDto findByOrderId(Integer orderId);

    // 前台 爭議表格 申請
    DisputeOrder add(DisputeOrder disputeOrder);

    // 後台 爭議列表 顯示 & 依條件查詢
    Page<DisputeOrder> findByCriteria(
            Integer disputeOrderId, Integer orderId, Integer storeId, String storeName, String memberNickname,
            Integer disputeStatus, LocalDate dateStart, LocalDate dateEnd, Integer page, Integer size
    );

    // 後台 爭議明細 顯示
    OrderDto findByDisputeOrderId(Integer disputeOrderId);

    // 後台 爭議明細 修改 > 爭議狀態
    DisputeOrder updateInfo(DisputeOrder disputeOrder);

}