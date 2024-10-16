package idv.tia201.g2.web.linebot.viewmodel;

import lombok.Data;

import java.util.List;

@Data
public class PushModel {

    private String to;
    private List<MsgModel> messages;

}
