package com.ssafy.happyhouse.controller.news;

import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.global.crawling.NewsCrawling;
import com.ssafy.happyhouse.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    /**
     * 당일 크롤링된 뉴스를 조회
     * @return
     * @throws IOException
     */
    @GetMapping("/related")
    public ResponseEntity<?> getRelatedNews() {
        List<News> news = newsService.getRelatedNews();

        if(news.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(news);

    }

    @GetMapping("/summarize-today")
    public ResponseEntity<?> getNewsSummary() {
        List<String> answer = newsService.getNewsSummary();
        return ResponseEntity.ok(answer);
    }



}
