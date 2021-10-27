package com.wootech.dropthecode.config;

import java.util.Map;

import com.wootech.dropthecode.controller.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.exception.AuthenticationException;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class StompHandler implements HandshakeInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    public StompHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String chattingToken = request.getURI().getQuery();
        if (!jwtTokenProvider.validateToken(chattingToken)) {
            throw new AuthenticationException("유효하지 않은 유저입니다.");
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}