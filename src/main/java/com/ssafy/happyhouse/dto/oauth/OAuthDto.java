package com.ssafy.happyhouse.dto.oauth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.happyhouse.entity.member.constant.Role;
import com.ssafy.happyhouse.global.token.JwtTokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class OAuthDto {

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Request {

        private String memberType;

        private String accessToken;

        private String refreshToken;

        private String accessTokenExpireTime;

        private String refreshTokenExpireTime;
    }

    @Data
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Response {

        private Long id;

        private String username;

        private Role role;

        private String nickname;

        private String memberType;

        private String profile;

        // 토큰 관련 정보 리턴
        private String grantType;

        private String accessToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpireTime;

        private String refreshToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpireTime;

        // ResponseDto에 of 메서드를 작성 (Service에 만들지 않고 숨김)
        public static Response of(JwtTokenDto tokenDto){
            return Response.builder()
                    .id(tokenDto.getId())
                    .role(tokenDto.getRole())
                    .username(tokenDto.getUsername())
                    .nickname(tokenDto.getNickname())
                    .profile(tokenDto.getProfile())
                    .memberType(tokenDto.getMemberType())
                    .grantType(tokenDto.getGrantType())
                    .accessToken(tokenDto.getAccessToken())
                    .accessTokenExpireTime(tokenDto.getAccessTokenExpireTime())
                    .refreshToken(tokenDto.getRefreshToken())
                    .refreshTokenExpireTime(tokenDto.getRefreshTokenExpireTime())
                    .build();
        }
    }
}
