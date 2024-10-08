package idv.tia201.g2.web.chat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.tia201.g2.web.chat.dao.ChatSessionRepository;
import idv.tia201.g2.web.chat.dto.MessageDto;
import idv.tia201.g2.web.chat.service.ChatRoomService;
import idv.tia201.g2.web.chat.service.MessageService;
import idv.tia201.g2.web.chat.vo.Messages;
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
    //依 TotalUserId 對應 WebSocketSession
    private static final Map<Long, WebSocketSession> SESSIONS_MAP = new ConcurrentHashMap<>();

    private static final Logger logger = LogManager.getLogger(ChatWebSocketHandler.class);

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MessageService messageService;

    @Autowired
    ChatRoomService chatRoomService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //將 user & session 進行保存
        HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");

        TotalUserDTO user = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        Long totalUserId = user.getTotalUserId();

        SESSIONS_MAP.put(totalUserId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage text) throws Exception {
        // 1. 將 text 轉成 messageDto
        //將訊息轉換成 json
        String json = text.getPayload();
        //於將 JSON 字串與 Java 對象進行轉換
//        ObjectMapper objectMapper = new ObjectMapper();
        //將 JSON 字串 json 轉換為 TotalUsers 類型的 Java 對象。
        MessageDto messageDto = objectMapper.readValue(json, MessageDto.class);
        System.out.println("ChatWebSocketHandler" + messageDto);

        // 2. 從 messageDto 獲取 接收方的訊息
        HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");
        TotalUserDTO user = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        Long recipientsId;
        System.out.println("ChatWebSocketHandler 的 chatSessionId:" + messageDto.getChatSessionId());
        //管理員視角
        if (user.getUserTypeId() == 3) {
            recipientsId = chatSessionRepository.findAttenderIdByChatSessionId(messageDto.getChatSessionId());
            System.out.println("管理員發送");
        //其他視角
        } else {
            recipientsId = chatSessionRepository.findAdministratorIdByChatSessionId(messageDto.getChatSessionId());
            System.out.println("用戶發送");
        }
        System.out.println("ChatWebSocketHandler 中 recipientsId :  " + recipientsId);

        // 3. 如果是文字訊息，就存入資料庫
        if (messageDto.getMessageId() == null) {      //傳送的是文字
            //存訊息
            Messages message = messageService.saveMessage(messageDto);
            //更新聊天室時間
            chatRoomService.updateLastActivity(message);
        }

        // 4. 接收方有連線，就傳遞訊息
        WebSocketSession recipientSession = SESSIONS_MAP.get(recipientsId);     //接收方的websocketSesseion
        if (recipientSession != null && recipientSession.isOpen()) {
            SESSIONS_MAP.get(recipientsId).sendMessage(text);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");
        TotalUserDTO user = (TotalUserDTO) httpSession.getAttribute("totalUserDTO");
        SESSIONS_MAP.remove(user);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");
        TotalUsers user = (TotalUsers) httpSession.getAttribute("totalUserDTO");
        logger.error(exception.getMessage(), exception);
        SESSIONS_MAP.remove(user);
    }

}
