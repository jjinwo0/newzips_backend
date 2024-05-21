package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.entity.news.NewsLetter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {

    // 부동산 연관 뉴스 조회
    List<News> getRelatedNews();

    // 뉴스레터 구독자 조회
    List<String> getRecipients();

    // 뉴스레터 저장
    void writeNewsLetter(NewsLetter newsLetter);
}
