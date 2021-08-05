package com.wootech.dropthecode.config;

import java.util.List;

import com.wootech.dropthecode.controller.auth.AuthenticationInterceptor;
import com.wootech.dropthecode.controller.auth.GetAuthenticationInterceptor;
import com.wootech.dropthecode.controller.auth.LoginMemberArgumentResolver;
import com.wootech.dropthecode.service.AuthService;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthService authService;

    public WebMvcConfig(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .exposedHeaders("*");

        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(authService))
                .addPathPatterns("/teachers", "/teachers/me", "/members/me", "/reviews", "/reviews/**", "/logout");
        registry.addInterceptor(new GetAuthenticationInterceptor(authService))
                .addPathPatterns("/reviews/student/**", "/members/me");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver(authService));
    }
}
