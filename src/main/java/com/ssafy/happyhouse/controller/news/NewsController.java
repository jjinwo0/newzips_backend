package com.ssafy.happyhouse.controller.news;

import com.ssafy.happyhouse.dto.board.BoardDto;
import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.global.crawling.NewsCrawling;
import com.ssafy.happyhouse.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * 당일 요약된 뉴스를 조회
     * @return
     */
    @GetMapping("/summarize-today")
    public ResponseEntity<?> getNewsSummary() {
        List<String> answer = newsService.getNewsSummary();

        return ResponseEntity.ok(answer);
    }

    /**
     * 구독자 대상으로 전송할 뉴스레터를 저장
     * @param dto
     * @return
     * @throws Exception
     */
    @PostMapping("/send-letter")
    public ResponseEntity<?> sendNewsLetter(@RequestBody BoardDto.Write dto) {
        newsService.sendNewsLetter(dto);

        return ResponseEntity.ok().build();
    }


}
