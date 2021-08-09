package com.wootech.dropthecode.config;

import com.wootech.dropthecode.domain.CustomDataSourceProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CustomDataSourceProperties.class)
public class CustomDataSourceConfig {
}
