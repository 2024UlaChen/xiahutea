package idv.tia201.g2.web.chat.controller;

import idv.tia201.g2.web.chat.dto.MessageDto;
import idv.tia201.g2.web.chat.service.MessageService;
import idv.tia201.g2.web.chat.vo.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatmessagesdata")
public class ChatMessageController {

    @Autowired
    MessageService messageService;


    @GetMapping("{chatId}")
    public List<MessageDto> getChatMessagesData(@PathVariable Integer chatId) throws IOException {
        List<MessageDto> chatMessagesData = new ArrayList<>();
        chatMessagesData = messageService.getChatMessagesData(chatId);

        return chatMessagesData;
    }

    @PostMapping("imgmessage")
    public MessageDto saveImgMessage (@RequestBody MessageDto messageDto) throws IOException {
        Messages message = messageService.saveMessage(messageDto);
        return messageService.MessagesToMessageDto(message);
    }

    @GetMapping("message/{messageId}")
    public MessageDto ImgMessage(@PathVariable Long messageId) throws IOException {
        return  messageService.getImgMessageDTO(messageId);

    }

}
