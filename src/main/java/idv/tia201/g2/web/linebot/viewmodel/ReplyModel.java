package idv.tia201.g2.web.linebot.viewmodel;

import lombok.Data;

import java.util.List;
@Data
public class ReplyModel {
    private String replyToken;
    private List<MsgModel> messages;
}
