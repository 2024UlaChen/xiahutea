package idv.tia201.g2.web.chat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.tia201.g2.web.chat.dao.ChatSessionDao;
import idv.tia201.g2.web.chat.vo.Messages;
import idv.tia201.g2.web.user.vo.TotalUsers;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    //依 chatSessionId
    private static final Map<Long, WebSocketSession> SESSIONS_MAP = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    private static final Logger logger = LogManager.getLogger(ChatWebSocketHandler.class);

    @Autowired
    private ChatSessionDao chatSessionDao;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //將 user & session 進行保存
        this.httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");
        TotalUsers user = (TotalUsers) httpSession.getAttribute("totalUserDTO");
        SESSIONS_MAP.put(user.getTotalUserId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage text) throws Exception {
        // 1. 將 message 轉成 messages
        //將訊息轉換成 json
        String json = text.getPayload();
        //於將 JSON 字串與 Java 對象進行轉換
        ObjectMapper objectMapper = new ObjectMapper();
        //將 JSON 字串 json 轉換為 TotalUsers 類型的 Java 對象。
        Messages messages = objectMapper.readValue(json, Messages.class);

        // 2. 從 messages 獲取 接收方的訊息
        Long recipientsId;
        TotalUsers user = (TotalUsers) this.httpSession.getAttribute("totalUserDTO");
        //管理員視角
        if (user.getUserTypeId() == 3) {
            recipientsId = chatSessionDao.findAttenderIdByChatSessionId(messages.getChatSessionId());
        } else {
            recipientsId = chatSessionDao.findAdministratorIdByChatSessionId(messages.getChatSessionId());
        }

        SESSIONS_MAP.get(recipientsId).sendMessage(text);

//        for (WebSocketSession connectedSession : connectedSessionSet) {
//            if (connectedSession.isOpen()) {
//                connectedSession.sendMessage(message);
//            } else {
//                connectedSessionSet.remove(connectedSession);
//            }
//        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        TotalUsers user = (TotalUsers) this.httpSession.getAttribute("totalUserDTO");
        SESSIONS_MAP.remove(user);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        TotalUsers user = (TotalUsers) this.httpSession.getAttribute("totalUserDTO");
        logger.error(exception.getMessage(), exception);
        SESSIONS_MAP.remove(user);
    }

}
