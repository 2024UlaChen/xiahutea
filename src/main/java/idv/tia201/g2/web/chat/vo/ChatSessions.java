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
    @Column(name = "chat_session_id")
    private Integer chatSessionsid;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "last_activity")
    private Timestamp lastActivity;
    @Column(name = "chat_status_id")
    private Integer chatStatusId;
    @Column(name = "attender_id")
    private Integer attenderId;
    @Column(name = "administrator_id")
    private Integer administratorId;

}
