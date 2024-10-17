package idv.tia201.g2.web.linebot.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import idv.tia201.g2.web.linebot.service.LineBotService;
import idv.tia201.g2.web.linebot.viewmodel.MsgModel;
import idv.tia201.g2.web.linebot.viewmodel.PushModel;
import idv.tia201.g2.web.linebot.viewmodel.ReplyModel;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class LineBotServiceImpl implements LineBotService {
    private StoreDao storeDao;
    private String replyURL = "https://api.line.me/v2/bot/message/reply";
    private String pushURL = "https://api.line.me/v2/bot/message/push";
    @Autowired
    public LineBotServiceImpl(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    private final String token ="/TgGvfzSgkoT2D0Q3N8PeUOfIzFBzCbpmko1cQ79NQSgzcAg3aoBj/Nsox9uQ3+2DoIy8xpblP68ckNNwWHiX9/QVRkWYnVrfR+CljjPouZeci590hdi8xgkwmnvSKA2byA7J8ZWmm1Kzc7JKD8QEwdB04t89/1O/w1cDnyilFU=";
    @Override
    public void SendMessage(String message, String replyToken) throws IOException {
        ReplyModel replyModel = new ReplyModel();
        replyModel.setReplyToken(replyToken);
        MsgModel msgModel = new MsgModel();
        msgModel.setType("text");
        msgModel.setText(message);
        List<MsgModel> msgModelList = new ArrayList<>();
        msgModelList.add(msgModel);
        replyModel.setMessages(msgModelList);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(replyModel);
        System.out.println(jsonString);
        ConnectUrl(jsonString,replyURL);



    }

    @Override
    public void StoreAddressList(String address,String replyToken) throws IOException {

        //字串處理
        address = address.split("台灣")[1];
        address = address.substring(0,6);
        List<Store> storeList = storeDao.findByStoreAddressContainingAndStoreStatus(address,1);
        StringBuilder data = new StringBuilder();
        // 建立API訊息的結構

        for (Store store : storeList) {
            String shellTime = store.getOpeningHours().toString().split(":")[0]+":"+store.getOpeningHours().toString().split(":")[1]+"~"+store.getClosingHours().toString().split(":")[0]+":"+store.getClosingHours().toString().split(":")[1];
            String storeName ="店名:"+store.getStoreName();
            String sotreAddress ="地址:"+store.getStoreAddress();
            String storeShellTime = "營業時間:"+shellTime;


            String row = "店名:"+store.getStoreName()+"地址:"+store.getStoreAddress()+"營業時間:"+shellTime;
            // String row = "店名:"+store.getStoreName()+"\n地址:"+store.getStoreAddress()+"\n營業時間:"+shellTime+"\n";
            data.append(row);
        }
        System.out.println(data.toString());

        SendMessage(data.toString(),replyToken);
    }



    @Override
    public void ConnectUrl(String message,String Url) throws MalformedURLException, IOException {

        URL lineServieUrlForSend = new URL(Url);

        HttpURLConnection connection = (HttpURLConnection) lineServieUrlForSend.openConnection();//建立連接
        connection.setRequestMethod("POST");
        //設置header
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Authorization", "Bearer "+this.token);
        connection.setDoOutput(true); //post lien service
        connection.setDoInput(true); //接收line service
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(message.getBytes("UTF-8"));//發送到lien server
        wr.flush();//清空緩存區
        wr.close();
        System.out.println(connection.getResponseCode());
    }

    @Override
    public void SendStoreInfo(String address, String userId) throws IOException {
        //字串處理
        address = address.split("台灣")[1];
        address = address.substring(0,6);
        List<Store> storeList = storeDao.findByStoreAddressContainingAndStoreStatus(address,1);

        // 建立API訊息的結構
        List<MsgModel> msgModelList = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        if(storeList.isEmpty()){
            text.append("此區無店家資訊");

        }else{
            for (Store store : storeList) {


                String shellTime = store.getOpeningHours().toString().split(":")[0]+":"+store.getOpeningHours().toString().split(":")[1]+"~"+store.getClosingHours().toString().split(":")[0]+":"+store.getClosingHours().toString().split(":")[1];
                String storeName ="店名:"+store.getStoreName();

                String sotreAddress ="地址:"+store.getStoreAddress();

                String storeShellTime = "營業時間:"+shellTime;

                text.append(storeName).append("\n").append(sotreAddress).append("\n").append(storeShellTime).append("\n");


            }
        }


        MsgModel msg = new MsgModel();
        msg.setType("text");
        msg.setText(text.toString());
        msgModelList.add(msg);
        //建立回復物件
        PushModel pushModel = new PushModel();
        pushModel.setTo(userId);
        pushModel.setMessages(msgModelList);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(pushModel);
        System.out.println(jsonString);
        ConnectUrl(jsonString,pushURL);

    }

    @Override
    public void AutoReply(String Keyword, String userId) throws IOException {
        //抓關鍵字回復
        // 建立API訊息的結構
        List<MsgModel> msgModelList = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        MsgModel msg = new MsgModel();

        if(Keyword.contains("訂餐")){

            text.append("在首頁點擊店家即可查看該店家的菜單並進行訂單。");

        } else if (Keyword.contains("門市資訊")) {
            text.append("請查看菜單上的店家資訊顯示該店家詳細營運資料。");

        } else if (Keyword.contains("優惠券折扣")) {
            text.append("結帳頁面可於優惠使用功能-優惠券，根據使用者擁有優惠券進行選用折抵。");
        }else if (Keyword.contains("手機認證碼")) {
            text.append("可能會導致收不到簡訊驗證碼的情況有以下幾種，建議您嘗試以下方法：\n" +
                    "1. 請您再次確認於手機號碼欄位所選擇的國碼及輸入的手機號碼是否正確。\n" +
                    "2. 訊號不佳或者網路壅塞皆會導致無法收取驗證碼，建議可至訊號通順良好的地方再次獲取驗證碼，或者稍後重新獲取即可。\n" +
                    "3.請確認是否有設定來電攔截APP (e.g. Whoscall)，如果有設定到黑名單，由於驗證簡訊通常為系統大量發送訊息，很可能被手機判為廣告，請解除攔截或封鎖，避免簡訊接收失敗。\n" +
                    "4. 若您使用iOS手機，訊息裡的設定請不要使用 3rd party 程式來封鎖/過濾黑名單聯絡人，避免簡訊接收失敗。\n" +
                    "5. 部分電信業者有提供的＂拒收企業簡訊＂功能，該功能可能導致無法正常接受驗證碼簡訊。解除流程可能因業者系統升級而有所不同，請見電信業者官網為主，若需查詢、關閉此功能請自行洽詢所屬電信商。\n" +
                    "\n" +
                    "以中華電信為例，消費者有2個確認方式：\n" +
                    "\n" +
                    "1. 登入emome網站後，點選「我的emome」－「熱門服務」中，點選「拒絕企業簡訊」，確認此服務是否為「未申請」狀態，若申請可能導致簡訊停用而無法正常接收簡訊。\n" +
                    "（※關閉此服務後，約需4小時作業時間後才可重新接收企業簡訊。若不熟悉網站操作步驟，可致電各電信業者客服，透過電話語音操作完成服務變更。）\n" +
                    "2. 以市話或手機撥打電信業者客服電話，經過身份驗證後，向客服人員詢問是否有申請「拒收企業簡訊」服務。\n除了上述情況外，若仍無法收受簡訊驗證碼，您可依以下方式進行手機操作排除以下狀況：\n" +
                    "1. 確認手機儲存空間，若儲存空間已滿將無法收受簡訊驗證碼。\n" +
                    "2. 將手機重新啟用。\n" +
                    "3.若上述仍無法排除，請您加入官方客服，由客服人員協助驗證作業。");
        }else if (Keyword.contains("忘記密碼")) {
            text.append("登入頁面點選忘記密碼，輸入手機號碼後按下重設密碼，系統會發送驗證碼到手機，輸入密碼&簡訊驗證碼後點選重設密碼，驗證&重設成功會跳出視窗並導到登入畫面");
        }else if (Keyword.contains("找不到訂單")) {
            text.append("請確認下訂時與現在是否為相同帳號 (相同入徑登入)。");
        }
        else if (Keyword.contains("外送")) {
            text.append("外送服務請查看首頁上的貨車Icon，可以判斷是否提供外送。");
        }
        else if (Keyword.contains("品項有誤")) {
            text.append("""
                    若收到商品內容錯誤時(商品錯誤或有遺漏商品..等問題)，請點選訂單管理，選擇商品有誤的訂單，進入訂單明細，可於訂單明細內，提出申請爭議。\n
                    夏虎茶確認爭議內容後，將於聊天室與您進一步確認。\n
                    再請您留意聊天室訊息，謝謝。""");



        }
        else if (Keyword.contains("收到退款")) {
            text.append("申請退款後，由夏虎茶向店家確認實際狀況後，進行退款至會員錢包，故訂單列表之爭議狀態顯示「同意爭議」時，系統將同步將款項退至會員錢包，可至會員中心進行確認退款款項。");
        }
        else if (Keyword.contains("載具")) {
            text.append("1. 須請公司會計單位使用“公司帳號“登入，選擇「營業人」進入「財政部電子發票整合服務平台」查詢/列印。\n" +
                    "2. 使用“個人帳號“登入財政部電子發票整合服務平台的話，僅能看到簡易明細，無法下載正式的電子發票證明。\n" +
                    "3. https://www.mof.gov.tw/singlehtml/155?cntId=84135\n" +
                    "財政部電子發票整合服務平台官方資訊參考");
        }
        else if (Keyword.contains("取得優惠券")) {
            text.append("優惠券會由平台方發放 可在首頁點擊優惠券查看擁有那些優惠券。");
        }
        else if (Keyword.contains("使用優惠券")) {
            text.append("結帳頁面可於優惠使用功能-優惠券，根據使用者擁有優惠券進行選用折抵。");
        }else if (Keyword.contains("有什麼優惠券") || Keyword.contains("優惠券使用期限")) {
            text.append("可在右上角會員中心點選優惠券，查看擁有的優惠券。");
        }
        else if (Keyword.contains("獲得店家集點卡")) {
            text.append("需要店家有啟動集點卡功能，並且消費滿足店家要求額度即可累積一點。");
        }
        else if (Keyword.contains("點數兌換")) {
            text.append("結帳頁面可於使用功能-會員卡，根據使用者於該店面會員點數進行累點折抵。");
        }
        else if (Keyword.contains("哪些集點卡") || Keyword.contains("集點卡使用期限")) {
            text.append("在首頁點擊會員卡即可查看各集點卡資訊。");
        }else{
            text.append("成功收到你的輸入，但未觸發任何功能");
        }


        msg.setType("text");
        msg.setText(text.toString());
        msgModelList.add(msg);
        PushModel pushModel = new PushModel();
        pushModel.setTo(userId);
        pushModel.setMessages(msgModelList);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(pushModel);
        System.out.println(jsonString);
        ConnectUrl(jsonString,pushURL);
    }


}
