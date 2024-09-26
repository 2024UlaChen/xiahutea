package idv.tia201.g2.web.chat.dto;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class MessageDto {
    private Long messageId;
    private Integer chatSessionId;
    private String content;
    private ImgDto img;
    private Long senderId;
    private Timestamp timestamp;
    private Long recipientId;
}
