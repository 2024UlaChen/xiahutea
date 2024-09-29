package idv.tia201.g2.web.chat.service.impl;

import idv.tia201.g2.web.chat.dao.ChatSessionRepository;
import idv.tia201.g2.web.chat.dto.ChatRoom;
import idv.tia201.g2.web.chat.service.ChatRoomService;
import idv.tia201.g2.web.chat.vo.ChatSessions;
import idv.tia201.g2.web.chat.vo.Messages;
import idv.tia201.g2.web.user.dao.TotalUserDao;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomServiceimpl implements ChatRoomService {

    @Autowired
    private ChatSessionRepository chatSessionDao;

    public final static long ADMINTOTALUSERID = 1;

    @Override
    public List<ChatRoom> getChatRoom(TotalUserDTO user) {
        Integer userType = user.getUserTypeId();            //會員種類
        Long userTotalUserId = user.getTotalUserId();       //全平台ID
        List<ChatRoom> chatRoomData = chatSessionDao.findChatRoomData(userType, userTotalUserId);
        return chatRoomData;
    }

    public ChatSessions updateLastActivity (Messages message){
        // 依聊天室 Message 的 聊天室 ID 找尋對應聊天室
        Optional<ChatSessions> result = chatSessionDao.findById(message.getChatSessionId());
        ChatSessions chatSessions = result.get();
        //更新該聊天室更新時間
        chatSessions.setLastActivity(message.getSentAt());
        //存回去
        return chatSessionDao.save(chatSessions);
    }

    public ChatSessions addChatRoom(TotalUserDTO totalUserDTO) {
        ChatSessions chatSession = new ChatSessions();
        chatSession.setAttenderId(totalUserDTO.getTotalUserId());
        chatSession.setAdministratorId(ADMINTOTALUSERID);
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        chatSession.setCreatedAt(createdAt);
        return chatSessionDao.save(chatSession);
    }
}


