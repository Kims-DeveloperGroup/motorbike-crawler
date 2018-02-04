package com.devoo.kim.crawler.naver;

import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.parser.NaverSearchResultDocumentParser;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AsyncNaverCafeSearchCrawler.class, NaverSearchResultDocumentParser.class})
public class AsyncNaverCafeSearchCrawlerIT {
    @Autowired
    private AsyncNaverCafeSearchCrawler asyncNaverCafeSearchCrawler;
    //then
    private final int ITEM_COUNT_IN_SINGLE_PAGE = 10;

    @Test
    public void shouldTheResultDocumentHasSamePageNumberAsGivenPageNumber_whenRetreivingDocumentFromSearchResultPage() throws UnsupportedEncodingException {
        //Given
        String testQueryString = "네이버";
        int expectedPageNumber = 3;

        //When
        Mono<String> mono = asyncNaverCafeSearchCrawler.getDocument(testQueryString, expectedPageNumber);
        Document document = Jsoup.parse(mono.block());
        String actualPageNumberString
                = document.getElementById("pageArea").getElementsByTag("strong").get(0).text();
        //Then
        int actualPageNumber = Integer.parseInt(actualPageNumberString);
        assertThat(actualPageNumber).isEqualTo(expectedPageNumber);
    }

    @Test
    public void shouldBeGettingDocumentCalledAsMuchAsPageLimit_whenSearchingItemsForGivenQuery() throws InterruptedException {
        //Given
        String query = "cbr 125";
        int pageLimit = 3;

        //When
        List<NaverItem> naverItems = asyncNaverCafeSearchCrawler.search(query, pageLimit);
        Thread.sleep(5000L);
        Assertions.assertThat(naverItems).hasSize(pageLimit * ITEM_COUNT_IN_SINGLE_PAGE);
    }
}