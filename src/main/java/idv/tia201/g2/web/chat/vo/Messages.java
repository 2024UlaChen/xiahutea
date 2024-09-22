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
    private Long messageId;
    private Integer chatSessionId;
    private String messageContent;
    private byte[] attach;
    private Long senderId;
    private Timestamp sentAt;

}
