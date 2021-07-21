package com.wootech.dropthecode.controller.auth;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

import com.wootech.dropthecode.controller.auth.util.AuthorizationExtractor;
import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.oauth.Login;
import com.wootech.dropthecode.service.AuthService;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthService authService;

    public LoginMemberArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class)
                && LoginMember.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = AuthorizationExtractor
                .extract(Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
        return authService.findMemberByToken(accessToken);
    }
}
