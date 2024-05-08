package com.ssafy.happyhouse.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDTO {

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Request {

        String username;

        String password;
    }
}
