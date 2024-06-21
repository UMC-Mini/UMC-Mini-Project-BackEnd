package com.miniproject.demo.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**/")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://localhost:3001")
                .allowedMethods("POST", "GET", "DELETE", "PUT")
                .allowedHeaders("*");
    }
}
