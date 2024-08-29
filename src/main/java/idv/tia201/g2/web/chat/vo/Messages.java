package idv.tia201.g2.web.chat.vo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;
    @Column(name = "chat_session_id")
    private Integer chatSessionId;
    @Column(name = "message_content")
    private String messageContent;
    private byte[] attach;
    @Column(name = "sender_id")
    private Long senderId;
    @Column(name = "sent_at")
    private Timestamp sentAt;

}
