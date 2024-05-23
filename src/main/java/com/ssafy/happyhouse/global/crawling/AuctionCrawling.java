package com.ssafy.happyhouse.global.crawling;

import com.ssafy.happyhouse.entity.auction.Auction;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.lang.model.element.Element;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@AllArgsConstructor
@Slf4j
@Component
public class AuctionCrawling {

    public static String extractNumber(String data) {
        String[] parts = data.split("\n");
        return parts[0];
    }

    public static String extractDate(String data) {
        String[] parts = data.split("\n");
        return parts[1].replace(".", "-");
    }

    /**
     * 지방법원에서 부동산 경매자료를 가져온다.
     * @param findDistrict
     * @return
     */
    public List<Auction> getAuctionInfo(String findDistrict) {
        List<Auction> auctionList = new ArrayList<>();
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        // WebDriver 초기화
        WebDriver driver = new ChromeDriver(options);


        try {
            driver.get("https://www.courtauction.go.kr/");

            // WebDriverWait 객체 생성 (최대 10초 대기)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));


            // 현재 창의 핸들을 저장
            String mainWindowHandle = driver.getWindowHandle();
            System.out.println(mainWindowHandle + "============= 메인");

            // 팝업 창을 여는 작업 수행 (예: 버튼 클릭)
            // driver.findElement(By.id("popupButton")).click();

            // 모든 창 핸들을 가져옴
            Set<String> allWindowHandles = driver.getWindowHandles();

            // 팝업 창으로 전환
            for (String windowHandle : allWindowHandles) {
                System.out.println(windowHandle);
                if (!windowHandle.equals(mainWindowHandle)) {

                    driver.switchTo().window(windowHandle);
                    // 팝업 창에서 작업 수행
                    // 예: driver.findElement(By.id("closeButton")).click();
                    // 팝업 창 닫기
                    driver.close();
                    System.out.println("닫힘 =================");
                }
            }

            // 원래 창으로 전환
            driver.switchTo().window(mainWindowHandle);
            System.out.println("전환 ====================" + driver.getWindowHandles().size());
            Thread.sleep(5000);

            driver.switchTo().frame("indexFrame");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement detailSearchBtn = driver.findElement(By.xpath("//*[@id=\"menu\"]/h1[2]/a"));
            System.out.println(detailSearchBtn);
            // 물건 상세 검색
            detailSearchBtn.click();

            //지역 법원 검색
            WebElement dropdownElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idJiwonNm")));
            Select district = new Select(dropdownElement);

            district.selectByValue(findDistrict);

            // 대분류 중분류 소분류 선택과정
            Select firstSelect = new Select(driver.findElement(By.id("idLevel1")));
            firstSelect.selectByValue("0000802");

            Thread.sleep(1000);
            Select secondSelect = new Select(driver.findElement(By.id("idLevel2")));
            secondSelect.selectByValue("000080201");

            Thread.sleep(1000);
            Select thirdSelect = new Select(driver.findElement(By.id("idLevel3")));
            thirdSelect.selectByValue("00008020104");

            // 검색
            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#contents > form > div.tbl_btn > a:nth-child(1)")));
            searchBtn.click();



            // 페이지 끝까지 반복
            boolean hasNextPage = true;
            while (hasNextPage) {
                WebElement tbody = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#contents > div.table_contents > form:nth-child(1) > table > tbody")));
                if(tbody != null) {
                    List<WebElement> rows = tbody.findElements(By.tagName("tr"));
                    if(rows != null) {
                        if(rows.get(0).findElement(By.tagName("td")).getText().equals("검색결과가 없습니다.")) {
                            break;
                        }
                        for (WebElement row : rows) {

                            String court = findDistrict;
                            String usage = "주거용건물";
                            String location = row.findElement(By.cssSelector("td:nth-child(4) > div > a:nth-child(1)")).getText();
                            String appraisalValue = row.findElement(By.cssSelector("td.txtright > div.tbl_btm_noline")).getText();
                            String minimumSalePrice = row.findElement(By.cssSelector("td.txtright > div.tbl_btm_line")).getText();
                            String saleDate = row.findElement(By.cssSelector("td:nth-child(7) > div.tbl_btm_noline")).getText();
                            String status = row.findElement(By.cssSelector("td:nth-child(7) > div.tbl_btm_line")).getText();

                            Auction auction = Auction.builder()
                                    .court(court)
                                    .productUsage(usage)
                                    .location(location)
                                    .appraisalValue(appraisalValue)
                                    .minimumSalePrice(extractNumber(minimumSalePrice))
                                    .saleDate(extractDate(saleDate))
                                    .status(status)
                                    .build();

                            auctionList.add(auction);

                        }
                    }


                    // 페이지의 끝인지 확인
                    List<WebElement> pageSpans = driver.findElements(By.cssSelector("#contents > div.table_contents > form:nth-child(2) > div > div.page2 > span"));

                    if (pageSpans.isEmpty()) {
                        System.out.println("Page span element not found, exiting.");
                        break;
                    }

                    WebElement pageSpan = pageSpans.get(0);
                    List<WebElement> nextPageLink = pageSpan.findElements(By.xpath("following-sibling::a"));

                    if (nextPageLink.size() > 0 && nextPageLink.get(0).isDisplayed()) {
                        nextPageLink.get(0).click();
                        wait.until(ExpectedConditions.stalenessOf(pageSpan)); // 다음 페이지가 로드될 때까지 대기
                    } else {
                        hasNextPage = false;
                    }

                }

            }


        } catch (NoSuchElementException e) {
            // skip
        } catch (NoSuchWindowException e) {
            // skip
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
        return auctionList;
    }

}
