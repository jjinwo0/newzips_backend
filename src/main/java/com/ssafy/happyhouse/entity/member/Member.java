package com.ssafy.happyhouse.entity.member;

import com.ssafy.happyhouse.entity.member.constant.MemberType;
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
    private String nickname;

    @NotNull
    private String profile;

    @NotNull
    private Role role;

    @NotNull
    private MemberType memberType;

    @Size(max = 500)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @Builder
    public Member(String username, String email, String password, String nickname, String profile, Role role, MemberType memberType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.role = role;
        this.memberType = memberType;
    }
}
