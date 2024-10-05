package idv.tia201.g2.web.order.service;

import idv.tia201.g2.web.order.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 依商店 發送自家訂單新增訊息
    public void addOrderNotify(Integer orderId, Timestamp creatDate, Integer storeId) {
        // 新增訊息
        NotificationDto message = new NotificationDto();
        message.setType(1);
        message.setId(orderId);
        message.setCreateDatetime(creatDate);

        System.out.println("----------------發送訂單通知--商店ID: " + storeId + "，---通知內容: " + message);

        // 發送訊息
        messagingTemplate.convertAndSend("/store/notifications/" + storeId, message);
    }

    // 依商店 發送自家訂單新增訊息
    public void addDisputeNotify(Integer disputeId, Timestamp creatDate, Integer storeId) {

        // 新增訊息
        NotificationDto message = new NotificationDto();
        message.setType(2);
        message.setId(disputeId);
        message.setCreateDatetime(creatDate);

        System.out.println("----------------發送爭議通知--商店ID: " + storeId + "，---通知內容: " + message);

        // 發送訊息
        messagingTemplate.convertAndSend("/store/notifications/" + storeId, message);
    }
}