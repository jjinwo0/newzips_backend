package com.ssafy.happyhouse.entity.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class News {
    private Long id;
    private String newsDate;
    private String newsTitle;
    private String newsLink;
}
