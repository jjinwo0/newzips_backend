package com.ssafy.happyhouse.global.config;

import com.ssafy.happyhouse.global.error.FeignClientExceptionErrorDecoder;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.commons.httpclient.HttpClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients(basePackages = "com.ssafy")
@Import(FeignClientsConfiguration.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class, HttpClientConfiguration.class})
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {

        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new FeignClientExceptionErrorDecoder();
    }

    @Bean
    public Retryer retryer(){
        // period : 실행 주기
        // maxAttempts : 최대 재시도 횟수
        return new Retryer.Default(1000, 2000, 3);
    }
}
