package idv.tia201.g2.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("https://iorlvm.i234.me:8090") // 使用模式匹配
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true);
            }
        };
    }



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cms/chat")
                .allowedOrigins("https://iorlvm.i234.me:8090")
                .allowedMethods("GET", "POST")
                .allowCredentials(false);

        // 添加日志来确认配置是否被调用
        System.out.println("CORS mappings added for /cms/chat");
    }
}

