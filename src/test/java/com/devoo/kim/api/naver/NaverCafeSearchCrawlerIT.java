package com.devoo.kim.api.naver;

import com.devoo.kim.api.exception.NaverApiRequestException;
import org.assertj.core.api.Assertions;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NaverCafeSearchCrawler.class)
public class NaverCafeSearchCrawlerIT {

    @Autowired
    private NaverCafeSearchCrawler naverCafeSearchCrawler;

    @Test
    public void shouldReturnedDocumentContainListOfResultItems_whenGetRequestIsSentToSearchPage() {
        //given
        String query = "네이버";
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
        Elements resultElements = naverCafeSearchCrawler.search("네이버", 1, 0)
                .get(0);

        //then
        Assertions.assertThat(resultElements.size()).isEqualTo(NaverCafeSearchCrawler.PAGE_SIZE);
    }


}