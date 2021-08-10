package com.wootech.dropthecode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //메시지 브로커가 지원하는 'WebSocket 메시지 처리'를 활성화한다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 메세지 브로커를 설정한다.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메모리 기반의 Simple Message Broker를 활성화하면서,
        // 메세지 브로커가 "/subscribe"으로 시작하는 주소의 subscriber들에게 Response를 전달할 수 있도록 한다.
        registry.enableSimpleBroker("/subscribe");

        // 클라이언트가 서버로 메시지 보낼 때 붙여야 하는 prefix
        registry.setApplicationDestinationPrefixes("/publish");
    }

    // websocket 연결용 STOMP EndPoint를 등록한다.
    // STOMP 프로토콜 사용: 메세지를 보낼 때 특정 url로 보내면, 해당 url을 subscribe한 사용자들을 알맞게 찾아서 전송
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-connection")
                .withSockJS();
    }
}
