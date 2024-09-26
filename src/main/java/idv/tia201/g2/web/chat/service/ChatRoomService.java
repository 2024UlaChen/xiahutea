package idv.tia201.g2.web.chat.service;

import idv.tia201.g2.web.chat.dto.ChatRoom;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.vo.TotalUsers;

import java.util.List;
import java.util.Set;

public interface ChatRoomService {
    List<ChatRoom> getChatRoom(TotalUserDTO user);
}
