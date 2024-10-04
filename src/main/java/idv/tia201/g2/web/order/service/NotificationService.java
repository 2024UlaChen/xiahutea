package idv.tia201.g2.web.order.service;

import idv.tia201.g2.web.order.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    // 依商店 發送自家訂單新增訊息
    public void addOrderNotify(NotificationDto message, Integer storeId) {
        System.out.println("----------------發送通知給商店ID: " + storeId + "，內容: " + message);
        messagingTemplate.convertAndSend("/store/notifications/" + storeId, message);
    }
}