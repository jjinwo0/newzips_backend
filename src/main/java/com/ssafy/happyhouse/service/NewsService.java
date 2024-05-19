package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;

    public List<News> getRelatedNews() {

        return newsMapper.getRelatedNews();
    }
}
