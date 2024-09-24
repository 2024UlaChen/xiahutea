package idv.tia201.g2.web.chat.dto;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.user.vo.TotalUsers;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
public class ChatRoom{

    private Integer chatId;
    private String lastMessage;
    private Timestamp lastMessageAt;
    private List<TotalUsers> participants;

}
