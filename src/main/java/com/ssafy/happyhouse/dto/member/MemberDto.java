package com.ssafy.happyhouse.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

public class MemberDto {

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Request {

        private String username;

        private String password;
    }

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Join {

        private String username;

        @Email
        private String email;

        private String password;

        private String nickname;

        private int isSubscribed;

        public void setIsSubscribed(String isSubscribed) {
            if(isSubscribed.equals("true")) this.isSubscribed = 1;
            else this.isSubscribed = 1;
        }



    }

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Update {

        private String username;

        @Email
        private String email;

        private String password;

        private String nickname;
    }
}
