package idv.tia201.g2.web.chat.controller;

import idv.tia201.g2.web.chat.vo.Messages;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

    @MessageMapping("talk") // ←訊息控制器⽅法的網址，⽤以接收訊息框
    @SendTo("/chat/chat") // ←訊息交換器的網址，⽤以傳遞訊息框
    public Messages talk(
            SimpMessageHeaderAccessor headerAccessor,
            Messages chatMessage
    ) {
        TotalUserDTO totalUserDTO = (TotalUserDTO) headerAccessor
                .getSessionAttributes()
                .get("totalUserDTO");
        chatMessage.setSenderId(totalUserDTO.getTotalUserId());
        return chatMessage;
    }
}
