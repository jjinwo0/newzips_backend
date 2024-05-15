package com.ssafy.happyhouse.service.oauth;

import com.ssafy.happyhouse.entity.member.constant.MemberType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> map;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> map) {

        this.map = map;
    }

    // TODO: 다른 Social Login Service Bean 추가 예정
    public static SocialLoginApiService getSocialLoginService(MemberType memberType) {

        String beanName = "";

        if (MemberType.KAKAO.equals(memberType))
            beanName = "kakaoLoginApiService";

        // Bean Name으로 구현체 추출
        return map.get(beanName);
    }
}
