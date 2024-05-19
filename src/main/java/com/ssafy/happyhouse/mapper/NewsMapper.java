package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.news.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {

    List<News> getRelatedNews();
}
