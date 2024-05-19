package com.ssafy.happyhouse.global.batch.service;

import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.global.batch.mapper.BatchMapper;
import com.ssafy.happyhouse.global.crawling.NewsCrawling;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchMapper mapper;
    private final NewsCrawling newsCrawling;

    /**
     * 당일 부동산 관련 상위 5개의 뉴스를 크롤링한다.
     * @throws Exception
     */
    @Transactional
    public void crawlingNews() throws Exception {

        List<News> newsList = newsCrawling.getRelatedNews();
        try {
            mapper.insertNews(newsList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }



}
