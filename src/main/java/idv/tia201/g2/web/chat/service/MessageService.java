package idv.tia201.g2.web.chat.service;

import idv.tia201.g2.web.chat.dto.MessageDto;
import idv.tia201.g2.web.chat.vo.Messages;

import java.util.List;

public interface MessageService {
    MessageDto MessagesToMessageDto(Messages m);

    List<MessageDto> getChatMessagesData(Integer chatId);
    Messages saveMessage ( MessageDto messageDto );
}
