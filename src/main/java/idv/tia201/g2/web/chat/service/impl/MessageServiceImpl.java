package idv.tia201.g2.web.chat.service.impl;

import idv.tia201.g2.web.chat.dao.MessageChatSessionRepository;
import idv.tia201.g2.web.chat.dto.ImgDto;
import idv.tia201.g2.web.chat.dto.MessageDto;
import idv.tia201.g2.web.chat.service.MessageService;
import idv.tia201.g2.web.chat.vo.Messages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageChatSessionRepository messageChatSessionRepository;

    @Override
    public MessageDto MessagesToMessageDto(Messages m) {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessageId(m.getMessageId());
        messageDto.setChatSessionId(m.getChatSessionId());
        messageDto.setContent(m.getMessageContent());
        byte[] attach = m.getAttach();
        if(attach != null && attach.length >0){
            ImgDto imgDto = new ImgDto();
            imgDto.setSrc(attach);
            messageDto.setImg(imgDto);
        }

        messageDto.setSenderId(m.getSenderId());
        messageDto.setTimestamp(m.getSentAt());
        return messageDto;
    }

    @Override
    public List<MessageDto> getChatMessagesData(Integer chatId) {
        List<Messages> messageByChatId = messageChatSessionRepository.findByChatSessionId(chatId);
        List<MessageDto> ChatMessagesData = new ArrayList<>();
        for (Messages m : messageByChatId) {
            MessageDto messageDto = MessagesToMessageDto(m);
            ChatMessagesData.add(messageDto);
        }
        return ChatMessagesData;
    }

    public Messages saveMessage ( MessageDto messageDto ){
        Messages message = new Messages();
        BeanUtils.copyProperties(messageDto,message);
        message.setMessageContent(messageDto.getContent());
        if(messageDto.getImg() != null && messageDto.getImg().getSrc() != null){
            message.setAttach(messageDto.getImg().getSrc());
        }
        message.setSenderId(messageDto.getSenderId());
        message.setSentAt(messageDto.getTimestamp());
        return messageChatSessionRepository.save(message);
    }
}
