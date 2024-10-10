package idv.tia201.g2.web.chat.service.impl;

import idv.tia201.g2.web.chat.util.ImageProcessor;
import idv.tia201.g2.web.chat.dao.MessageChatSessionRepository;
import idv.tia201.g2.web.chat.dto.ImgDto;
import idv.tia201.g2.web.chat.dto.MessageDto;
import idv.tia201.g2.web.chat.service.MessageService;
import idv.tia201.g2.web.chat.vo.Messages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageChatSessionRepository messageChatSessionRepository;

    @Override
    public MessageDto MessagesToMessageDto(Messages m) throws IOException {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessageId(m.getMessageId());
        messageDto.setChatSessionId(m.getChatSessionId());
        messageDto.setContent(m.getMessageContent());
        byte[] attach = m.getAttach();
        if(attach != null && attach.length >0){
            ImgDto imgDto = new ImgDto();
            String encoded = ImageProcessor.encodeImage(attach);
            imgDto.setSrc(encoded);
            messageDto.setImg(imgDto);
        }

        messageDto.setSenderId(m.getSenderId());
        messageDto.setTimestamp(m.getSentAt());
        return messageDto;
    }

    @Override
    public List<MessageDto> getChatMessagesData(Integer chatId) throws IOException {
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
            String src = messageDto.getImg().getSrc();
            // 檢查是否包含 "data:image" 前綴，並去除它
            if (src.startsWith("data:image")) {
                src = src.substring(src.indexOf(",") + 1);
            }

            try {
                byte[] decoded = Base64.getDecoder().decode(src);
                message.setAttach(decoded);
            } catch (IllegalArgumentException e) {
                // 處理 Base64 解碼錯誤
                System.err.println("Base64 解碼錯誤: " + e.getMessage());
            }
        }
        message.setSenderId(messageDto.getSenderId());
        message.setSentAt(messageDto.getTimestamp());
        return messageChatSessionRepository.save(message);
    }

    @Override
    public MessageDto getImgMessageDTO(Long messageId) throws IOException {
        Messages message = messageChatSessionRepository.findByMessageId(messageId);
        return MessagesToMessageDto(message);
    }
}
