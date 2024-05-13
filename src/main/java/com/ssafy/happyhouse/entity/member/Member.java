package com.ssafy.happyhouse.entity.member;

import com.ssafy.happyhouse.entity.member.constant.Role;
import com.ssafy.happyhouse.global.token.JwtTokenDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @NotNull
    private Long id;

    @NotNull
    private String username;

    @Email
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

    @NotNull
    private Role role;

    @Size(max = 500)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @Builder
    public Member(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void updateToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = jwtTokenDto.getRefreshTokenExpireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void expireToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }

}
