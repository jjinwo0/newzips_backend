package com.ssafy.happyhouse.entity.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class Board {

    private Long id;

    private String title;

    private String content;

    private String author;

    private String createdAt;

    private String updatedAt;
}
