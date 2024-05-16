package com.ssafy.happyhouse.global.config;

import com.ssafy.happyhouse.global.interceptor.AdminInterceptor;
import com.ssafy.happyhouse.global.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.ssafy.happyhouse.controller")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("http://localhost:5173", "http://localhost:8080") // http://localhost:5173 에서 오는 요청을 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 허용할 HTTP 메소드 지정
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키를 포함한 요청 허용
                .maxAge(3600); // pre-flight 요청의 캐시 시간(초 단위)
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/member/**", "/admin/**")
                .excludePathPatterns("/member/login", "/member/join", "/member/valid/**", "/member/logout", "/member/oauth/**")
                .order(1);

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .order(2);
    }
}
