package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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

    public String getSummary(String text) {
        String url = "https://api.openai.com/v1/chat/completions";

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "기사 제목줄테니 내용을 요약해줘: " + text);

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

    public List<String> getNewsSummary() {
        List<News> newsList = getRelatedNews();

        return newsList.stream()
                .map(news -> getSummary(news.getNewsTitle()))
                .collect(Collectors.toList());

    }

}
