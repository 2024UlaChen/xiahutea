package idv.tia201.g2.web.linebot.controller;
import com.linecorp.bot.model.message.TextMessage; // 發送文本消息

import com.linecorp.bot.model.response.BotApiResponse; // LINE API 回應模型
import com.linecorp.bot.client.LineMessagingClient; // LINE API 客戶端

import com.linecorp.bot.model.ReplyMessage;
import idv.tia201.g2.web.linebot.service.LineBotService;
import idv.tia201.g2.web.linebot.viewmodel.EventListViewModel;
import idv.tia201.g2.web.linebot.viewmodel.EventViewModel;
import idv.tia201.g2.web.linebot.viewmodel.MessageViewModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

@RestController
public class LineBotController {
    private final LineBotService lineBotService;

    @Autowired
    public LineBotController(LineBotService lineBotService) {
        this.lineBotService = lineBotService;
    }



    @PostMapping("/callback")
    public void handleWebhook(@RequestBody EventListViewModel list, HttpServletRequest request) throws IOException {
        // 處理來自 LINE 的請求
        System.out.println(list);
        for (EventViewModel event : list.getEvents()) {
            //判斷地址還是文字
            if(event.getType().equals("message")){
                System.out.println(event.getReplyToken());
                System.out.println();
                System.out.println(event.getMessage().getText());
                if(event.getMessage().getType().equals("text")){

                    if(event.getMessage().getText().contains("幫")){
                        String Url = request.getRequestURL().toString();
                        Url = Url.split("://")[1];
                        Url = Url.split(request.getRequestURI())[0];

                        String res = "https://"+Url+"/faq.html";
                        System.out.println(res);
                        lineBotService.SendMessage(res, event.getReplyToken());
                    }else {
                        System.out.println(event.getSource().getUserId());

                        lineBotService.AutoReply(event.getMessage().getText(), event.getSource().getUserId());
                    }

                }
                if(event.getMessage().getType().equals("location")){
                    //lineBotService.StoreAddressList(event.getMessage().getAddress(), event.getReplyToken());
                    lineBotService.SendStoreInfo(event.getMessage().getAddress(), event.getSource().getUserId());

                }
            }



        }

    }









}
