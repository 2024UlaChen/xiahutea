package idv.tia201.g2.core.config;

import idv.tia201.g2.core.filter.HttpSessionInterceptor;
import idv.tia201.g2.web.chat.websocket.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ChatWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "cms/chat")
                .addInterceptors(new HttpSessionInterceptor());  // 加入攔截器
    }
    //-----------------------------------------------------------------------

    // Notification
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket-endpoint") // ←註冊端⼝
                .withSockJS(); // ←啟⽤SockJS⽀援
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/msg") // ←訊息控制器的網址前綴字
                .enableSimpleBroker("/store/notifications"); // ←註冊訊息交換器
    }
}