package idv.tia201.g2.web.chat.dao;

import idv.tia201.g2.web.chat.vo.ChatSessions;
import idv.tia201.g2.web.user.vo.TotalUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChatSessionDao extends JpaRepository<ChatSessions, Integer> {

    Set<Integer> findChatSessionIdByAdministratorId(Long administratorId);

    Long findAttenderIdByChatSessionId(Integer chatSessionId);

    Long findAdministratorIdByChatSessionId(Integer chatSessionId);

    @Query(value = "select m.messageContent " +
            "from Messages m " +
            "JOIN ChatSessions c " +
            "ON m.chatSessionId = c.chatSessionId " +
            "where c.lastActivity = m.sentAt")
    String findLastMessageByChatId(Integer chatId);
}
