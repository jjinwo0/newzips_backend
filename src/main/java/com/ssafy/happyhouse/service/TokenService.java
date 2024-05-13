package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.token.AccessTokenResponseDto;
import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.global.token.GrantType;
import com.ssafy.happyhouse.global.token.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken){

        Member findMember = memberService.findUserByRefreshToken(refreshToken);

        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(findMember.getId(), findMember.getUsername(), findMember.getRole(), accessTokenExpireTime);

        return AccessTokenResponseDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
