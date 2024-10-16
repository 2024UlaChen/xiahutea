package idv.tia201.g2.web.linebot.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.MalformedURLException;

public interface LineBotService {
    void SendMessage(String message,String replyToken) throws IOException;
    void StoreAddressList(String address,String replyToken) throws IOException;

    void ConnectUrl(String message,String Url) throws MalformedURLException, IOException;
    void SendStoreInfo(String message,String replyToken) throws IOException;
    void AutoReply(String Keyword,String userId) throws IOException;
}
