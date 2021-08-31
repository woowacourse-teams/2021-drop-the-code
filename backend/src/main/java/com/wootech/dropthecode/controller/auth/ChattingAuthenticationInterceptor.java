package com.wootech.dropthecode.controller.auth;

import com.wootech.dropthecode.controller.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.exception.AuthenticationException;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class ChattingAuthenticationInterceptor implements ChannelInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    public ChattingAuthenticationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            validateToken(accessor);
        }
        return message;
    }


    private void validateToken(StompHeaderAccessor accessor) {
        if (!jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("Authorization"))) {
            throw new AuthenticationException("access token이 유효하지 않습니다.");
        }
    }
}
