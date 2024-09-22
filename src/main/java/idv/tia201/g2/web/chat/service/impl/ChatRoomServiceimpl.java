package idv.tia201.g2.web.chat.service.impl;

import idv.tia201.g2.web.chat.dao.ChatSessionDao;
import idv.tia201.g2.web.chat.dto.ChatRoom;
import idv.tia201.g2.web.chat.service.ChatRoomService;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChatRoomServiceimpl implements ChatRoomService {

    @Autowired
    private  ChatSessionDao chatSessionDao;

    @Override
    public Set<ChatRoom> getChatRoom(TotalUserDTO user) {
        Integer userType = user.getUserTypeId();
        Long totalUserId = user.getTotalUserId();
        Set<ChatRoom> chatRoomData = new HashSet<>();

        //如果是管理員
        if(userType == 3){
            Set<Integer> chatIdSet = chatSessionDao.findChatSessionIdByAdministratorId(totalUserId);
            for (Integer chatId : chatIdSet){
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setChatId(chatId);

            }


        }else {

        }
        return null;
    }


    //依 chatId 找尋最新訊息

}


