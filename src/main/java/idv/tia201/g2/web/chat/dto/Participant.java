package idv.tia201.g2.web.chat.dto;

import lombok.Data;

@Data
public class Participant {
    private Long userId;
    private String name;
    private byte[] avatar;
    private Integer type;
}
