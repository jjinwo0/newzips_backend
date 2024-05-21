package com.ssafy.happyhouse.global.batch.mapper;

import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.entity.news.NewsLetter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BatchMapper {

    // 당일 부동산 관련 상위 5개의 뉴스를 크롤링한다.
    void insertNews(List<News> newsList) throws Exception;

    // 전송되지 않은 뉴스레터 내역을 조회한다.
    List<NewsLetter> getNewsLetter();

    // 뉴스레터 보냈다고 설정
    void setNewsLetter(int id);
}
