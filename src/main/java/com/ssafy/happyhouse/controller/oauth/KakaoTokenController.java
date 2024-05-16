package com.ssafy.happyhouse.controller.oauth;

import com.ssafy.happyhouse.controller.feign.KakaoTokenClient;
import com.ssafy.happyhouse.dto.oauth.KakaoTokenDto;
import com.ssafy.happyhouse.dto.oauth.OAuthAttributes;
import com.ssafy.happyhouse.dto.oauth.OAuthDto;
import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.entity.member.constant.MemberType;
import com.ssafy.happyhouse.entity.member.constant.Role;
import com.ssafy.happyhouse.service.oauth.KakaoLoginService;
import com.ssafy.happyhouse.service.oauth.OAuthLoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoTokenClient kakaoTokenClient;

    private final KakaoLoginService kakaoLoginService;

    private final OAuthLoginService oAuthLoginService;

    @Value("${kakao.client.id}")
    private String clientId;

    @GetMapping("/oauth/kakao/callback")
    public void loginCallBack(String code, HttpServletResponse response) throws IOException {

        String contentType = "application/x-www-form-urlencoded;charset=utf-8";

        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .grant_type("authorization_code")
                .code(code)
                .redirect_url("http://localhost:8080/oauth/kakao/callback")
                .build();

        KakaoTokenDto.Response kakaoResponse = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
        String accessToken = kakaoResponse.getAccess_token();
        String refreshToken = kakaoResponse.getRefresh_token();

        String redirectUri = UriComponentsBuilder
                .fromUriString("http://localhost:5173/oauth/success")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        response.sendRedirect(redirectUri);
    }
}
