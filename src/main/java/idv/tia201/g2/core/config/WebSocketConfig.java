package idv.tia201.g2.core.config;

import idv.tia201.g2.core.filter.HttpSessionInterceptor;
import idv.tia201.g2.web.chat.websocket.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

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
                .setAllowedOriginPatterns("https://iorlvm.i234.me:8090")  // 允許所有跨域請求，或根據需要設置具體的域
                .addInterceptors(new HttpSessionInterceptor());  // 加入攔截器

    }
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(64 * 1024);  // 默認限制為 64KB
        registration.setSendBufferSizeLimit(512 * 1024); // 設置發送緩衝區限制為 512KB
    }




    //-----------------------------------------------------------------------

    // Notification
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket-endpoint") // ←註冊端⼝
                .setAllowedOrigins("https://iorlvm.i234.me:8090") // 确保允许的域
                .withSockJS(); // ←啟⽤SockJS⽀援
        System.out.println("STOMP endpoint registered at /websocket-endpoint");
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/msg") // ←訊息控制器的網址前綴字
                .enableSimpleBroker("/store/notifications"); // ←註冊訊息交換器
    }
}