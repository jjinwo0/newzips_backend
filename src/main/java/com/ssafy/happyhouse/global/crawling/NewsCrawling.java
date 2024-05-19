package com.ssafy.happyhouse.global.crawling;

import com.ssafy.happyhouse.entity.news.News;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsCrawling {

    private final String REAL_EASTATE_RELATED_NEWS_URL = "https://search.naver.com/search.naver?where=news&query=%EB%B6%80%EB%8F%99%EC%82%B0&sm=tab_opt&sort=0&photo=0&field=0&pd=4&ds=&de=&docid=&related=0&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so%3Ar%2Cp%3A1d&is_sug_officeid=0&office_category=0&service_area=0";

    public List<News> getRelatedNews() throws IOException {
        Document doc = Jsoup.connect(REAL_EASTATE_RELATED_NEWS_URL).get();
        List<News> news = new ArrayList<>();

        Elements newsItems = doc.select(".group_news ul.list_news li.bx");



        // 상위 5개 연관 뉴스를 가져온다.
        for(Element newsItem : newsItems) {
            if(news.size() == 5) break;

            Element titleElement = newsItem.selectFirst("a.news_tit");
            if (titleElement != null) {
                News newData = News.builder()
                        .newsTitle(titleElement.text())
                        .newsLink(titleElement.attr("href"))
                        .build();

                news.add(newData);
            }
        }

        return news;
    }
}
