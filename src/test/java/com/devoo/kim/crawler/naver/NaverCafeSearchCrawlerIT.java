package com.devoo.kim.crawler.naver;

import com.devoo.kim.crawler.exception.NaverApiRequestException;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.parser.NaverSearchResultDocumentParser;
import org.assertj.core.api.Assertions;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NaverCafeSearchCrawler.class, NaverSearchResultDocumentParser.class})
public class NaverCafeSearchCrawlerIT {

    @Autowired
    private NaverCafeSearchCrawler naverCafeSearchCrawler;

    @Test
    public void shouldReturnedDocumentContainListOfResultItems_whenGetRequestIsSentToSearchPage() throws IOException {
        //given
        String query = "125cc팝니다";
        int pageNumber = 0;

        //when
        Document document = naverCafeSearchCrawler.getDocuments(query, pageNumber);
        Elements resultElements = document.getElementById(NaverCafeSearchCrawler.ID_RESULT_ELEMENT)
                .getElementsByTag("li");
        //then
        assertThat(resultElements).isNotNull();
    }

    @Test
    public void shouldBeItemsRetrievedAsManyAsPageSize_forGivenQuery() throws NaverApiRequestException {

        //when
        List<NaverItem> resultItems = naverCafeSearchCrawler.search("125cc팝니다", 1, 0);

        //then
        Assertions.assertThat(resultItems.size()).isEqualTo(NaverCafeSearchCrawler.PAGE_SIZE);
    }

    @Test
    public void shouldBeDocumentPageTheGivenPageNumber_whenGettingDocument() throws IOException {
        int expectedPageNumber = 9;
        String query = "cbr125|츅125 거래|중고|판매";
        //when
        Document document = naverCafeSearchCrawler.getDocuments(query, --expectedPageNumber);
        //then
        String actualPageNumberString
                = document.getElementById("pageArea").getElementsByTag("strong").get(0).text();
        int actualPageNumber = Integer.parseInt(actualPageNumberString);
        assertThat(actualPageNumber).isEqualTo(expectedPageNumber);
    }
}