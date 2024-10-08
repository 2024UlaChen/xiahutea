package idv.tia201.g2.web.chat.service;

import idv.tia201.g2.web.chat.dto.ChatRoom;
import idv.tia201.g2.web.chat.vo.ChatSessions;
import idv.tia201.g2.web.chat.vo.Messages;
import idv.tia201.g2.web.user.dto.TotalUserDTO;

import java.io.IOException;
import java.util.List;

public interface ChatRoomService {
    List<ChatRoom> getChatRoom(TotalUserDTO user) throws IOException;

    ChatSessions updateLastActivity(Messages message);

    ChatSessions addChatRoom ( TotalUserDTO totalUserDTO );
}
