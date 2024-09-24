package idv.tia201.g2.web.chat.dao;

import idv.tia201.g2.web.chat.vo.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Messages, Integer> {
    List<Messages> findByChatSessionId(Integer chatId);
}
