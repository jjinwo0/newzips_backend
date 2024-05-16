package com.ssafy.happyhouse.service.oauth;

import com.ssafy.happyhouse.controller.feign.KakaoLoginClient;
import com.ssafy.happyhouse.dto.oauth.KakaoLoginResponseDto;
import com.ssafy.happyhouse.dto.oauth.OAuthAttributes;
import com.ssafy.happyhouse.entity.member.constant.MemberType;
import com.ssafy.happyhouse.global.token.GrantType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoLoginService implements SocialLoginApiService{

    private final KakaoLoginClient client;
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {

        log.info("accessToken: {}", accessToken);

        KakaoLoginResponseDto kakaoUserInfo = client.getKakaoUserInfo(CONTENT_TYPE, GrantType.BEARER.getType() + " " + accessToken);

        log.info("kakaoUserInfo: {}", kakaoUserInfo.getId());
        log.info("profile: {}", kakaoUserInfo.getKakaoAccount().getProfile());

        KakaoLoginResponseDto.KakaoAccount account = kakaoUserInfo.getKakaoAccount();

        String email = account.getEmail();

        return OAuthAttributes.builder()
                .id(Long.parseLong(kakaoUserInfo.getId()))
                // email은 입력 필수값으로 지정할 수 없었기 때문에, email이 담겨왔는지 먼저 체크
                // email이 없으면 id값을 저장할 수 있도록 설계
                .email(!StringUtils.hasText(email) ? kakaoUserInfo.getId() : email)
                .username(!StringUtils.hasText(email) ? kakaoUserInfo.getId() : email.split("@")[0])
                .nickname(account.getProfile().getNickname())
                .profile(account.getProfile().getThumbnailImageUrl())
                .memberType(MemberType.KAKAO)
                .build();
    }
}
