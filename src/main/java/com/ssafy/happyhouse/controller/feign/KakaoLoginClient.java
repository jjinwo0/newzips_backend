package com.ssafy.happyhouse.controller.feign;

import com.ssafy.happyhouse.dto.oauth.KakaoLoginResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "https://kapi.kakao.com", name = "kakaoLoginClient")
public interface KakaoLoginClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoLoginResponseDto getKakaoUserInfo(@RequestHeader("Content-Type") String contentType,
                                           @RequestHeader("Authorization") String accessToken);

    @PostMapping(value = "/v1/user/logout", consumes = "application/json")
    Long logoutKakaoUser(@RequestHeader("Content-Type") String contentType,
                         @RequestHeader("Authorization") String accessToken);
}
