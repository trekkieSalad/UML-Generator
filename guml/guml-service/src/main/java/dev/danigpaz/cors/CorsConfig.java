package dev.danigpaz.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/guml/generate")
            .allowedOrigins("http://localhost:5500")
            .allowedMethods("POST");


        registry.addMapping("/guml/generate/image")
            .allowedOrigins("http://localhost:5500")
            .allowedMethods("POST");
    }
}

