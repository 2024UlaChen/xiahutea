package idv.tia201.g2.web.linebot.viewmodel;

import lombok.Data;

@Data
public class MessageViewModel {
    private String id;
    private String type;
    private String quoteToken;
    private String text;
    //file物件
    private String fileName;
    private String fileSize;
    //地點
    private String address;
    private String latitude;
    private String longitude;
    //表情
    private String stickerId;
    private String packageId;



}
