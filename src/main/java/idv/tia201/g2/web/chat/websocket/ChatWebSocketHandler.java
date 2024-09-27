package idv.tia201.g2.web.chat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.tia201.g2.web.chat.dao.ChatSessionRepository;
import idv.tia201.g2.web.chat.dto.MessageDto;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
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
    private ChatSessionRepository chatSessionRepository;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //將 user & session 進行保存
        this.httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");
        System.out.println(httpSession);
        TotalUserDTO user = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        Long totalUserId = user.getTotalUserId();
        System.out.println(totalUserId);

        SESSIONS_MAP.put(totalUserId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage text) throws Exception {
        // 1. 將 text 轉成 messageDto
        //將訊息轉換成 json
        String json = text.getPayload();
        //於將 JSON 字串與 Java 對象進行轉換
        ObjectMapper objectMapper = new ObjectMapper();
        //將 JSON 字串 json 轉換為 TotalUsers 類型的 Java 對象。
        MessageDto messageDto = objectMapper.readValue(json, MessageDto.class);

        // 2. 從 messages 獲取 接收方的訊息
        Long recipientsId;
        TotalUserDTO user = (TotalUserDTO) this.httpSession.getAttribute("totalUserDTO");
        //管理員視角
        if (user.getUserTypeId() == 3) {
            recipientsId = chatSessionRepository.findAttenderIdByChatSessionId(messageDto.getChatSessionId());
            //其他視角
        } else {
            recipientsId = chatSessionRepository.findAdministratorIdByChatSessionId(messageDto.getChatSessionId());
        }

        // 3. 接收方有連線，就傳遞訊息，否則移除 Map
        if (SESSIONS_MAP.get(recipientsId).isOpen()) {
            SESSIONS_MAP.get(recipientsId).sendMessage(text);
        } else {
            SESSIONS_MAP.remove(SESSIONS_MAP.get(recipientsId));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        TotalUserDTO user = (TotalUserDTO) this.httpSession.getAttribute("totalUserDTO");
        SESSIONS_MAP.remove(user);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        TotalUsers user = (TotalUsers) this.httpSession.getAttribute("totalUserDTO");
        logger.error(exception.getMessage(), exception);
        SESSIONS_MAP.remove(user);
    }

}
