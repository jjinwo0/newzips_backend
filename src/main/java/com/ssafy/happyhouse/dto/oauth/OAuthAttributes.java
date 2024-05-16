package com.ssafy.happyhouse.dto.oauth;

import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.entity.member.constant.MemberType;
import com.ssafy.happyhouse.entity.member.constant.Role;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OAuthAttributes {

    private Long id;

    private String nickname; // TODO: nickname 컬럼 추가 예정

    private String username;

    private String email;

    private String profile; // TODO: Profile Image 추가

    private MemberType memberType;

    public Member toMemberEntity(MemberType memberType, Role role) {

        // OAuthAttributes 정보를 담아 Member 객체로 만든 후, 해당 객체로 회원가입 진행
        return Member.builder()
                .id(id)
                .memberType(memberType)
                .role(role)
                .password("1234")
                .email(email)
                .username(email.split("@")[0])
                .image(profile)
                .nickname(nickname)
                .build();
    }
}
