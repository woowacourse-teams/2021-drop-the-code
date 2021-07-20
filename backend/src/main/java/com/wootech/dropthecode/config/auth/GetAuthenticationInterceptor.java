package com.wootech.dropthecode.config.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wootech.dropthecode.config.auth.service.AuthService;

public class GetAuthenticationInterceptor extends AuthenticationInterceptor {

    public GetAuthenticationInterceptor(AuthService authService) {
        super(authService);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isPreflight(request) || !isGet(request)) {
            return true;
        }
        validatesToken(request);
        return true;
    }
}
