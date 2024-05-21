package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.board.BoardDto;
import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.entity.news.NewsLetter;
import com.ssafy.happyhouse.global.common.GoogleMail;
import com.ssafy.happyhouse.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api.key}")
    private String apiKey;

    /**
     * 당일 크롤링된 뉴스를 조회
     * @return
     */
    public List<News> getRelatedNews() {

        return newsMapper.getRelatedNews();
    }

    /**
     * 뉴스를 제공하면 gpt가 요약
     * @param news
     * @return
     */
    public String getSummary(News news) {

        // 텍스트 길이 제한 (예: 2000자)
//        int maxLength = 2000;
//        String content = null;
//        if (news.getNewsContent().length() > maxLength) {
//            content = news.getNewsContent().substring(0, maxLength) + "...";
//        }

        String text = "제목 : " + news.getNewsTitle() + " 내용 : " + news.getNewsContent();
        String url = "https://api.openai.com/v1/chat/completions";

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "기사의 제목과 내용을 줄테니 요약된 내용을 반환해줘 반환한 내용 앞에는 제공한 제목을 꼭 붙여서 반환해줘,  " + text);

        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-4-turbo");
        request.put("messages", Collections.singletonList(message));

        return restTemplate.postForObject(url, createHttpEntity(request), String.class);
    }

    private HttpEntity<Map<String, Object>> createHttpEntity(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        return new HttpEntity<>(body, headers);
    }

    /**
     * 당일 뉴스를 조회해서 요약한 뉴스를 반환
     * @return
     */
    public List<String> getNewsSummary() {
        List<News> newsList = getRelatedNews();

        return newsList.stream()
                .map(news -> getSummary(news))
                .collect(Collectors.toList());

    }

    /**
     * 뉴스레터 수신자를 조회해서 배치 테이블에 저장
     * @param dto
     */
    @Transactional
    public void sendNewsLetter(BoardDto.Write dto) {
        List<String> recipients = newsMapper.getRecipients();

        NewsLetter newsLetter = NewsLetter.builder()
                .recipients(String.join(",", recipients))
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        newsMapper.writeNewsLetter(newsLetter);

    }
}
