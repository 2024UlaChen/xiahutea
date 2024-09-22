package idv.tia201.g2.web.chat.service;

import idv.tia201.g2.web.chat.dto.ChatRoom;
import idv.tia201.g2.web.user.dto.TotalUserDTO;

import java.util.Set;

public interface ChatRoomService {
    Set<ChatRoom> getChatRoom(TotalUserDTO user);
}
