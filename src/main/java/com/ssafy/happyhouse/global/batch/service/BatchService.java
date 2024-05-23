package com.ssafy.happyhouse.global.batch.service;

import com.ssafy.happyhouse.entity.auction.Auction;
import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.entity.news.NewsLetter;
import com.ssafy.happyhouse.global.batch.mapper.BatchMapper;
import com.ssafy.happyhouse.global.common.AESUtil;
import com.ssafy.happyhouse.global.common.GoogleMail;
import com.ssafy.happyhouse.global.crawling.AuctionCrawling;
import com.ssafy.happyhouse.global.crawling.NewsCrawling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatchService {

    private final BatchMapper mapper;
    private final NewsCrawling newsCrawling;
    private final AuctionCrawling auctionCrawling;
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

    @Transactional
    public void crawlingAuctionsInfo() throws Exception {

        try {
            List<Auction> auctionList = new ArrayList<>();
            String[] courtArr = {"서울중앙지방법원", "서울동부지방법원", "서울서부지방법원", "서울남부지방법원", "서울북부지방법원",
                    "의정부지방법원", "고양지원","남양주지원", "인천지방법원", "부천지원", "수원지방법원", "성남지원", "여주지원",
                    "평택지원", "안산지원", "안양지원", "춘천지방법원", "강릉지원", "원주지원", "속초지원", "영월지원", "청주지방법원",
                    "충주지원", "제천지원", "영동지원", "대전지방법원", "홍성지원", "논산지원","천안지원","공주지원","서산지원","대구지방법원",
                    "안동지원","경주지원","김천지원","상주지원","의성지원","영덕지원","포항지원","대구서부지원","부산지방법원","부산동부지원",
                    "부산서부지원","울산지방법원","창원지방법원", "마산지원","진주지원","통영지원","밀양지원","거창지원","광주지방법원","목포지원",
                    "장흥지원","순천지원","해남지원","전주지방법원","군산지원","정읍지원","남원지원","제주지방법원"
            };

            for(String court : courtArr) {
                log.info("=========================== 크롤링 지방법웝 " + court);
                auctionList.addAll(auctionCrawling.getAuctionInfo(court));
            }


            if(auctionList.size() > 0) {
                mapper.insertAuctionsInfo(auctionList);
            }

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

    public void setAuctionInfo() {

        List<Auction> auctions =  mapper.getAuctionInfo();

        String clientId = ""; // 네이버 클라우드 플랫폼에서 발급받은 Client ID
        String clientSecret = ""; // 네이버 클라우드 플랫폼에서 발급받은 Client Secret
        try {

            for(Auction auction : auctions) {

                String address = auction.getLocation();
                String encodedAddress = URLEncoder.encode(address, "UTF-8");
                String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + encodedAddress;

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
                con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else { // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }

                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                log.info("Response: " + response.toString());

                // JSON 파싱
                JSONObject jsonObj = new JSONObject(response.toString());
                JSONArray addresses = jsonObj.getJSONArray("addresses");
                if (addresses.length() > 0) {
                    JSONObject firstAddress = addresses.getJSONObject(0);
                    double lat = firstAddress.getDouble("x");
                    double lng = firstAddress.getDouble("y");

                    String sido = "";
                    String gugun = "";
                    String dongmyun = "";

                    JSONArray addressElements = firstAddress.getJSONArray("addressElements");
                    for (int i = 0; i < addressElements.length(); i++) {
                        JSONObject element = addressElements.getJSONObject(i);
                        JSONArray types = element.getJSONArray("types");
                        for (int j = 0; j < types.length(); j++) {
                            String type = types.getString(j);
                            if (type.equals("SIDO")) {
                                sido = element.getString("longName");
                            } else if (type.equals("SIGUGUN")) {
                                gugun = element.getString("longName");
                            } else if (type.equals("DONGMYUN")) {
                                dongmyun = element.getString("longName");
                            }
                        }
                    }

                    auction.setLat(String.valueOf(lat));
                    auction.setLng(String.valueOf(lng));
                    auction.setSido(sido);
                    auction.setGugun(gugun);
                    auction.setDong(dongmyun);
                }
                if(auction.getLng() != null && auction.getLat() != null && auction.getSido() != null && auction.getGugun() != null && auction.getDong() != null ) {
                    mapper.updateAuctionInfo(auction);
                }

            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
