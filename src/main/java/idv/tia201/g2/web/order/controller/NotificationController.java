package idv.tia201.g2.web.order.controller;

import idv.tia201.g2.web.order.dto.NotificationDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    // -------- FINISH ---------------------------------
    // 後台 發送訂單通知
    @MessageMapping("notifications") // ←訊息控制器⽅法的網址，⽤以接收訊息框
    @SendTo("/store/notifications/{storeId}") // ←訊息交換器的網址，⽤以傳遞訊息框
    public NotificationDto sendNotification(NotificationDto message) {
        return message;
    }
}