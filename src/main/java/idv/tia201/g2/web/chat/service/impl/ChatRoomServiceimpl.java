package idv.tia201.g2.web.chat.service.impl;

import idv.tia201.g2.web.chat.dao.ChatSessionDao;
import idv.tia201.g2.web.chat.dto.ChatRoom;
import idv.tia201.g2.web.chat.service.ChatRoomService;
import idv.tia201.g2.web.chat.vo.ChatSessions;
import idv.tia201.g2.web.user.dao.TotalUserDao;
import idv.tia201.g2.web.user.vo.TotalUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChatRoomServiceimpl implements ChatRoomService {

    @Autowired
    private  ChatSessionDao chatSessionDao;

    @Autowired
    private TotalUserDao totalUserDao;

    @Override
    public Set<ChatRoom> getChatRoom(TotalUsers user) {
        Integer userType = user.getUserTypeId();            //使用者會員種類
        Long userTotalUserId = user.getTotalUserId();       //使用者ID
        Set<ChatRoom> chatRoomData = new HashSet<>();       //回傳資料陣列
        Set<ChatSessions> chatSessionsSet;                  //聊天室陣列
        Integer adminType = 3;

        // 如果是管理員
        if (userType == adminType) {
            // 找到該管理員對應的所有聊天室
            chatSessionsSet = chatSessionDao.findByAdministratorId(userTotalUserId);
            // 管理員之外
        }else {
            // 找到消費者或店家對應的聊天室
            chatSessionsSet = chatSessionDao.findByAttenderId(userTotalUserId);
        }
        //  如果沒有對應的聊天室
        if (chatSessionsSet.isEmpty()) {
            return chatRoomData;
        }
        //  如果有對應的聊天室
        for (ChatSessions chatSession : chatSessionsSet) {
            //  建立符合前端所需資料的 chatRoom
            ChatRoom chatRoom = new ChatRoom();

            //將所需資料匯入
            Integer chatSessionId = chatSession.getChatSessionId();
            chatRoom.setChatId(chatSessionId);

            chatRoom.setLastMessage(chatSessionDao.findLastMessageByChatId(chatSessionId));

            chatRoom.setLastMessageAt(chatSession.getLastActivity());

            List<TotalUsers> participants = new ArrayList<>();
            participants.add(user);
            if (userType.equals(adminType)) {
                TotalUsers attender = totalUserDao.findByTotalUserId(chatSession.getAttenderId());
                participants.add(attender);
            } else {
                TotalUsers admin = totalUserDao.findByTotalUserId(chatSession.getAdministratorId());
                participants.add(admin);
            }
            chatRoom.setParticipants(participants);
            chatRoomData.add(chatRoom);
        }
        return chatRoomData;
    }
}


