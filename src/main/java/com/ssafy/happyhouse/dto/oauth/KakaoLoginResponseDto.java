package com.ssafy.happyhouse.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class KakaoLoginResponseDto {

    private String id;

    private KakaoAccount account;

    @Getter @Builder
    public static class KakaoAccount {

        private String email;

        private Profile profile;

        @Getter @Builder
        public static class Profile {

            private String nickname;

            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;
        }
    }
}
