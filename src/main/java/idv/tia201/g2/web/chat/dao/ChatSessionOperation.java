package idv.tia201.g2.web.chat.dao;

import idv.tia201.g2.web.chat.dto.ChatRoom;

import java.io.IOException;
import java.util.List;

public interface ChatSessionOperation {

    List<ChatRoom> findChatRoomData(Integer userTypeId, Long userId) throws IOException;
}
