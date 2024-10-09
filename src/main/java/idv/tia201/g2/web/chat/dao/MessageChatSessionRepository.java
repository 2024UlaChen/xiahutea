package idv.tia201.g2.web.chat.dao;

import idv.tia201.g2.web.chat.vo.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageChatSessionRepository extends JpaRepository<Messages, Integer> {
    @Query(value = "from Messages m where m.chatSessionId = :chatSessionId ORDER BY m.sentAt DESC")
    List<Messages> findByChatSessionId(Integer chatSessionId);

    @Query(value = "from Messages m where m.messageId = :messageId")
    Messages findByMessageId(Long messageId);
}
