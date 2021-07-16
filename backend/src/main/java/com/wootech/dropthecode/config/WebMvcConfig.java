package com.wootech.dropthecode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTION")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowCredentials(true)
                .exposedHeaders("*");

        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
