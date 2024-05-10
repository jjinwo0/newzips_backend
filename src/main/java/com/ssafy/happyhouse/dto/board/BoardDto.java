package com.ssafy.happyhouse.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BoardDto {

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Write{

        private String title;

        private String content;

        private String author;
    }

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Update{

        private String title;

        private String content;
    }
}
