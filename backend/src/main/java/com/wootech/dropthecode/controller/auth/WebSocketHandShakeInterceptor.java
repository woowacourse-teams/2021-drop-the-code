package com.wootech.dropthecode.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println(request.getMethod());
        System.out.println(request.getMethodValue());
        System.out.println(request.getHeaders());



        System.out.println("TQ");

//        if (request.getHeaders().getConnection().contains(HttpHeaders.UPGRADE)) {
//
//        }

//        if (request instanceof HttpServletRequest) {
//            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
//
//        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
