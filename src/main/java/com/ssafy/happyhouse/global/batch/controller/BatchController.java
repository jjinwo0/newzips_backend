package com.ssafy.happyhouse.global.batch.controller;

import com.ssafy.happyhouse.global.batch.service.BatchService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BatchController {


    private static final Logger log = LoggerFactory.getLogger(BatchController.class);
    private final BatchService batchService;

    // 매일 오전 7시에 실행
    @Scheduled(cron = "0 0 7 * * ?")
    //@Scheduled(fixedDelay = 50000)
    public void getRelatedNews() throws Exception {
        batchService.crawlingNews();
        log.info("============================ 뉴스 크롤링 완료");
    }

    // 5분마다 보내야할 이메일을 조회하여 전송한다
    @Scheduled(cron = "0 */5 * * * ?")
    //    //@Scheduled(fixedDelay = 50000)
    public void sendEmail() throws Exception {
        batchService.sendEmail();
        log.info("============================ 이메일 전송 완료");
    }

    // 매일 오후 5시에 실행
    //@Scheduled(cron = "0 0 17 * * ?")
    //@Scheduled(fixedDelay = 50000)
    public void crawlingAuctionsInfo() throws Exception {
        batchService.crawlingAuctionsInfo();
        log.info("====================== 부동산 경매 정보 크롤링 완료");
    }

    //@Scheduled(fixedDelay = 50000)
    public void setAuctionInfo() throws Exception {
        batchService.setAuctionInfo();
    }



}
