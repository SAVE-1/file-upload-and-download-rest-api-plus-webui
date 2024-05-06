package com.filesharing.filebin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/**")
                .allowedOrigins("https://127.0.0.1", "http://localhost:5173")
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowedHeaders("header1", "header2", "header3")
                .exposedHeaders("header1", "Access-Control-Allow-Origin");
                //.allowCredentials(true).maxAge(3600);

        // Add more mappings...
    }
}
