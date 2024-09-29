package idv.tia201.g2.web.chat.dao;

import idv.tia201.g2.web.chat.vo.ChatSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSessions, Integer>, ChatSessionOperation {

    Set<ChatSessions> findByAdministratorId(Long administratorId);

    @Query(value = "select cs.attenderId from ChatSessions cs where cs.chatSessionId = :chatSessionId")
    Long findAttenderIdByChatSessionId(@Param("chatSessionId") Integer chatSessionId);

    @Query(value = "select cs.administratorId from ChatSessions cs where cs.chatSessionId = :chatSessionId")
    Long findAdministratorIdByChatSessionId(@Param("chatSessionId") Integer chatSessionId);

    @Query(value = "select m.messageContent " +
            "from Messages m " +
            "JOIN ChatSessions c " +
            "ON m.chatSessionId = c.chatSessionId " +
            "where c.lastActivity = m.sentAt and m.chatSessionId = :chatId")
    String findLastMessageByChatId(Integer chatId);

    Set<ChatSessions> findByAttenderId(Long AttenderId);
}
