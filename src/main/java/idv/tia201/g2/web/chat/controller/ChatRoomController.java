package idv.tia201.g2.web.chat.controller;

import idv.tia201.g2.web.chat.dto.ChatRoom;
import idv.tia201.g2.web.chat.service.ChatRoomService;
import idv.tia201.g2.web.user.dao.TotalUserDao;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chatroomdata")
public class ChatRoomController {

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    TotalUserDao totalUserDao;

    @GetMapping()
    public List<ChatRoom> getChatRoomData (HttpSession session){
        TotalUserDTO user = (TotalUserDTO) session.getAttribute("totalUserDTO");
        List<ChatRoom> chatRoomData = chatRoomService.getChatRoom(user);
        return chatRoomData;
    }
}
