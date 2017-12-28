package com.devoo.kim.api.naver;

import com.devoo.kim.api.exception.NaverApiRequestException;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.parser.NaverSearchResultElementsParser;
import org.assertj.core.api.Assertions;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NaverCafeSearchCrawler.class, NaverSearchResultElementsParser.class})
public class NaverCafeSearchCrawlerIT {

    @Autowired
    private NaverCafeSearchCrawler naverCafeSearchCrawler;

    @Test
    public void shouldReturnedDocumentContainListOfResultItems_whenGetRequestIsSentToSearchPage() {
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
}