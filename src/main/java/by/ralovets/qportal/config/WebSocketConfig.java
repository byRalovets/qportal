package by.ralovets.qportal.config;

import by.ralovets.qportal.controller.util.CustomHandshakeHandler;
import by.ralovets.qportal.controller.util.WebsocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/responses-endpoint")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .withSockJS();
        registry.addEndpoint("/responses-endpoint")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new CustomHandshakeHandler());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(websocketInterceptor());
    }

    @Bean
    public WebsocketInterceptor websocketInterceptor() {
        return new WebsocketInterceptor();
    }
}
