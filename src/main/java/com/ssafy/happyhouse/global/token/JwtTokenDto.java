package com.ssafy.happyhouse.global.token;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.happyhouse.entity.member.constant.Role;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenDto {

    private Long id;
    private String username;
    private Role role;
    private String grantType;
    private String accessToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date accessTokenExpireTime;
    private String refreshToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date refreshTokenExpireTime;

    @Override
    public String toString() {
        return "JwtTokenDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", grantType='" + grantType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", accessTokenExpireTime=" + accessTokenExpireTime +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshTokenExpireTime=" + refreshTokenExpireTime +
                '}';
    }
}
