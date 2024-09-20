package idv.tia201.g2.web.chat.vo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "chat_sessions")
@Setter
@Getter
public class ChatSessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatSessionId;
    private Timestamp createdAt;
    private Timestamp lastActivity;
    private Integer chatStatusId;
    private Long attenderId;
    private Long administratorId;

}
