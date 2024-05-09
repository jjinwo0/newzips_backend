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
    }

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Update {

        private String username;

        @Email
        private String email;

        private String password;
    }
}
