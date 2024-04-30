package com.boot.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        DemoInterceptor demoInterceptor = new DemoInterceptor();
        String[] paths = {"/**"};

        String[] excludePath = {"/demo/**"};
        registry.addInterceptor(demoInterceptor).addPathPatterns(paths).excludePathPatterns(excludePath);
    }
}
