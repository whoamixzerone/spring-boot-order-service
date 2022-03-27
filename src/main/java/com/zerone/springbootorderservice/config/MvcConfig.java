package com.zerone.springbootorderservice.config;

import com.zerone.springbootorderservice.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    public MvcConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**",
                        "/v3/api-docs", "/swagger-ui/**")
                .excludePathPatterns("/member/login");
    }
}