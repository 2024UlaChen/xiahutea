package idv.tia201.g2.web.linebot.viewmodel;

import lombok.Data;

@Data
public class EventViewModel {
    private String replyToken;
    private SourceViewModel source;
    private String type;
    private MessageViewModel message;
    private String timestamp;
}
