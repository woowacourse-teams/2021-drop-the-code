package com.wootech.dropthecode.config.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wootech.dropthecode.config.auth.service.AuthService;
import com.wootech.dropthecode.config.auth.util.AuthorizationExtractor;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    public AuthenticationInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPreflight(request) || isGet(request)) {
            return true;
        }
        validatesToken(request);
        return true;
    }

    protected void validatesToken(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        authService.validatesAccessToken(accessToken);
    }

    protected boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    protected boolean isGet(HttpServletRequest request) {
        return HttpMethod.GET.matches(request.getMethod());
    }
}
