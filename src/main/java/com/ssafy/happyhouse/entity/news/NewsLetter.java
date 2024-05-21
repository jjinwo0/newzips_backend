package com.ssafy.happyhouse.entity.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class NewsLetter {
    private int id;
    private String recipients;
    private String title;
    private String content;
    private String sendDate;
    private int isSend;
}
