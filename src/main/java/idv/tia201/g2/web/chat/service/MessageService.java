package idv.tia201.g2.web.chat.service;

import idv.tia201.g2.web.chat.dto.MessageDto;
import idv.tia201.g2.web.chat.vo.Messages;

import java.io.IOException;
import java.util.List;

public interface MessageService {
    MessageDto MessagesToMessageDto(Messages m) throws IOException;

    List<MessageDto> getChatMessagesData(Integer chatId) throws IOException;
    Messages saveMessage ( MessageDto messageDto );
    MessageDto getImgMessageDTO(Long messageId) throws IOException;
}
