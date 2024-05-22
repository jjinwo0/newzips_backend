package com.ssafy.happyhouse.global.batch.service;

import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.entity.news.NewsLetter;
import com.ssafy.happyhouse.global.batch.mapper.BatchMapper;
import com.ssafy.happyhouse.global.common.AESUtil;
import com.ssafy.happyhouse.global.common.GoogleMail;
import com.ssafy.happyhouse.global.crawling.NewsCrawling;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchMapper mapper;
    private final NewsCrawling newsCrawling;
    private final GoogleMail googleMail;

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

    /**
     *  전송되지 않은 이메일을 찾아서 전송한다.
     */
    public void sendEmail() throws Exception {
        try {
            List<NewsLetter> list = mapper.getNewsLetter();
            if (list != null && list.size() > 0) {
                for (NewsLetter newsLetter : list) {

                    String[] recipients = newsLetter.getRecipients().split(",");
                    for(String recipient : recipients) {


                        googleMail.sendmail(recipient, newsLetter.getTitle(), newsLetter.getContent());
                    }
                }
                for (NewsLetter newsLetter : list) {
                    mapper.setNewsLetter(newsLetter.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
