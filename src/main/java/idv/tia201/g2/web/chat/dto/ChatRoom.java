package idv.tia201.g2.web.chat.dto;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import lombok.Data;

import java.util.Set;

@Data
public class ChatRoom extends Core {

    private Integer chatId;
    private String lastMessage;
    private String lastMessageAt;
    private Set<TotalUserDTO> participants;

}
