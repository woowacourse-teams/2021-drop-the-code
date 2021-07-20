package com.wootech.dropthecode.config.auth.service;

import com.wootech.dropthecode.config.auth.util.JwtTokenProvider;
import com.wootech.dropthecode.exception.AuthorizationException;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void validatesAccessToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthorizationException("access token이 유효하지 않습니다.");
        }
    }
}
