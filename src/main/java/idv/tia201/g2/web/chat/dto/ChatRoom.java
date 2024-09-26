package idv.tia201.g2.web.chat.dto;

import idv.tia201.g2.web.user.vo.TotalUsers;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ChatRoom{

    private Integer chatId;
    private String lastMessage;
    private Timestamp lastMessageAt;
    private List<Participant> participants;

}
