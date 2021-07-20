package com.wootech.dropthecode.config;

import com.wootech.dropthecode.config.auth.AuthenticationInterceptor;
import com.wootech.dropthecode.config.auth.GetAuthenticationInterceptor;
import com.wootech.dropthecode.config.auth.service.AuthService;
import com.wootech.dropthecode.config.auth.util.JwtTokenProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .allowCredentials(true)
                .allowedOrigins("https://d1y4kq9j17q5cm.cloudfront.net/")
                .exposedHeaders("*");

        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(authService))
                .addPathPatterns("/teachers", "/reviews", "/reviews/**");
        registry.addInterceptor(new GetAuthenticationInterceptor(authService))
                .addPathPatterns("/reviews/student/**");
    }
}
