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

    @Data
    public static class Request {

        private String memberType;
    }

    @Data
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Response {

        private Long id;

        private String username;

        private Role role;

        // 토큰 관련 정보 리턴
        private String grantType;

        private String accessToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpireTime;

        private String refreshToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpireTime;

        // ResponseDto에 of 메서드를 작성 (Service에 만들지 않고 숨김)
        public static Response of(JwtTokenDto jwtTokenDto){
            return Response.builder()
                    .id(jwtTokenDto.getId())
                    .username(jwtTokenDto.getUsername())
                    .role(jwtTokenDto.getRole())
                    .grantType(jwtTokenDto.getGrantType())
                    .accessToken(jwtTokenDto.getAccessToken())
                    .accessTokenExpireTime(jwtTokenDto.getAccessTokenExpireTime())
                    .refreshToken(jwtTokenDto.getRefreshToken())
                    .refreshTokenExpireTime(jwtTokenDto.getRefreshTokenExpireTime())
                    .build();
        }
    }
}
