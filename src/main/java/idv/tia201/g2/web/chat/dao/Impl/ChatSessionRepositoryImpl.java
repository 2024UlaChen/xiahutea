package idv.tia201.g2.web.chat.dao.Impl;

import idv.tia201.g2.web.chat.dao.ChatSessionOperation;
import idv.tia201.g2.web.chat.dto.ChatRoom;
import idv.tia201.g2.web.chat.dto.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ChatSessionRepositoryImpl implements ChatSessionOperation {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ChatRoom> findChatRoomData(Integer userTypeId, Long userId) {
        String jpql =
                "select " +
                        "   chat.chat_session_id as chatId," +
                        "   m.message_content as lastMessage," +
                        "   chat.last_activity as lastMessageAt," +
                        "   attender.total_user_id as attenderId," +
                        "   case attender.user_type_id " +
                        "       when 0 then c.nickname " +
                        "       when 1 then s.store_name " +
                        "   end as attenderName," +
                        "   case attender.user_type_id " +
                        "       when 0 then c.customer_img " +
                        "       when 1 then s.logo " +
                        "   end as attenderAvatar," +
                        "   attender.user_type_id as attenderType," +
                        "   admin.total_user_id as adminId," +
                        "   a.admin_username as adminName," +
                        "   admin.user_type_id as attenderType " +
                        "from chat_sessions chat " +
                        "       join total_users attender on chat.attender_id = attender.total_user_id" +
                        "       join total_users admin on chat.administrator_id = admin.total_user_id" +
                        "       left join messages m on m.chat_session_id = chat.chat_session_id" +
                        "       left join customer c on c.customer_id = attender.user_id" +
                        "       left join store s on s.store_id = attender.user_id" +
                        "       join administrators a on a.administrator_id  = admin.user_id " +
                        "where " +
                        "   case ? " +
                            "   when 0 then attender.total_user_id = ?" +
                            "   when 1 then attender.total_user_id = ?" +
                            "   when 3 then admin.total_user_id = ? " +
                        "   end " +
                        "   and (m.sent_at = chat.last_activity or chat.last_activity is NULL) " +
                        "order by m.sent_at DESC";

        Query query = em.createNativeQuery(jpql)
                .setParameter(1, userTypeId)
                .setParameter(2, userId)
                .setParameter(3, userId)
                .setParameter(4, userId);
        List<Object[]> results = query.getResultList();

        List<ChatRoom> chatRoomsData = new ArrayList<>();

        // 0筆資料
        if (results.isEmpty()) {
            return chatRoomsData;
        }
        //有資料，將 select 的資料逐筆代入到 ChatRoomData
        for (Object[] result : results) {

            ChatRoom chatRoom = new ChatRoom();

            chatRoom.setChatId((Integer) result[0]);
            chatRoom.setLastMessage((String) result[1]);
            chatRoom.setLastMessageAt((Timestamp) result[2]);

            List<Participant> participantList = new ArrayList<>();

            Participant attender = new Participant();
            if (result[3] instanceof Long) {
                attender.setUserId((Long) result[3]); // 直接轉換為 Long
            } else if (result[3] instanceof BigInteger) {
                attender.setUserId(((BigInteger) result[3]).longValue());   // JPA 將整數類型的結果視為 BigInteger
            }
            attender.setName((String) result[4]);
            byte[] bytes = (byte[]) result[5];
            String base64Str = "data:image/png;base64,";
            if(bytes != null){
                base64Str += Base64.getEncoder().encodeToString(bytes);
            }
            attender.setAvatar(base64Str);
            attender.setType((Integer) result[6]);
            participantList.add(attender);

            Participant admin = new Participant();
            if (result[7] instanceof Long) {
                admin.setUserId((Long) result[7]); // 直接轉換為 Long
            } else if (result[7] instanceof BigInteger) {
                admin.setUserId(((BigInteger) result[7]).longValue());
            }
            admin.setName((String) result[8]);
            admin.setType((Integer) result[9]);
            participantList.add(admin);

            chatRoom.setParticipants(participantList);
            chatRoomsData.add(chatRoom);
        }
        return chatRoomsData;
    }
}
