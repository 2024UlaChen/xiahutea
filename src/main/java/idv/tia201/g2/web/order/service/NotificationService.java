package idv.tia201.g2.web.order.service;

import idv.tia201.g2.web.order.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyNewOrder(NotificationDto message) {
        messagingTemplate.convertAndSend("/store/notifications", message);
    }

//    public void notifyDisputeFinish(NotificationDto message){
//        messagingTemplate.convertAndSend("/store/notifications", message);
//    }

}