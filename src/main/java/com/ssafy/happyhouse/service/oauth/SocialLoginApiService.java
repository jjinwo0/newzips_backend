package com.ssafy.happyhouse.service.oauth;

import com.ssafy.happyhouse.dto.oauth.OAuthAttributes;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);
}
