package com.ssafy.happyhouse.controller.news;

import com.ssafy.happyhouse.dto.board.BoardDto;
import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.global.common.AESUtil;
import com.ssafy.happyhouse.global.crawling.NewsCrawling;
import com.ssafy.happyhouse.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final AESUtil aesUtil;

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

    /**
     * 구독 취소
     * @param orgEmail
     * @param encodedEmail
     * @return
     * @throws Exception
     */
    @GetMapping("/unsubscribe/{orgEmail}/{encodedEmail}")
    public ResponseEntity<?> unsubscribe(@PathVariable String orgEmail, @PathVariable String encodedEmail) throws Exception {
        try {
            String email = aesUtil.decrypt(encodedEmail, aesUtil.getKeyFromSeed());
            if (orgEmail.equals(email)) {
                newsService.unsubscribe(orgEmail);
                return ResponseEntity.ok("You have successfully unsubscribed.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid unsubscribe request.");
            }
        } catch (Exception e) {
            // 로그에 예외를 기록
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }

    @GetMapping("/check-subscribe/{memberId}")
    public ResponseEntity<?> checkSubscribe(@PathVariable Long memberId) {
        int isSubscribed = newsService.checkSubscribe(memberId);

        return ResponseEntity.ok(isSubscribed);
    }

    @PatchMapping("subscribe/{memberId}")
    public ResponseEntity<?> updateIssubscribe(@PathVariable Long memberId) {

        newsService.updateIssubscribe(memberId);

        return ResponseEntity.ok(true);
    }


}
