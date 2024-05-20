package com.ssafy.happyhouse.entity.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ADMIN"), USER("USER"), EXPERT("EXPERT");

    private final String roleType;

    public static Role from(String role) {
        return Role.valueOf(role);
    }
}
