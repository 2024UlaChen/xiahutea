package idv.tia201.g2.web.chat.controller;

import idv.tia201.g2.web.chat.dto.MessageDto;
import idv.tia201.g2.web.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatmessagesdata")
public class ChatMessageController {

    @Autowired
    MessageService messageService;


    @GetMapping("{chatId}")
    public List<MessageDto> getChatMessagesData(@PathVariable Integer chatId) {
        List<MessageDto> chatMessagesData = new ArrayList<>();
        chatMessagesData = messageService.getChatMessagesData(chatId);

        return chatMessagesData;
    }

}
