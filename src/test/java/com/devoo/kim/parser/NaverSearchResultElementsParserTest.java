package com.devoo.kim.parser;

import com.devoo.kim.api.exception.NaverApiRequestException;
import com.devoo.kim.api.naver.NaverCafeSearchCrawler;
import com.devoo.kim.domain.naver.NaverItem;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class NaverSearchResultElementsParserTest {

    private static NaverCafeSearchCrawler naverCafeSearchCrawler
            = new NaverCafeSearchCrawler("https://section.cafe.naver.com/ArticleSearch.nhn");
    private static Elements testResultElements;

    private NaverSearchResultElementsParser parser = new NaverSearchResultElementsParser();

    @BeforeClass
    public static void setUpTestData() throws NaverApiRequestException {
        testResultElements = naverCafeSearchCrawler.search("125cc팝니다", 1, 0).get(0);
    }

    @Test
    public void shouldBeGivenRawElementsParsedToNaverItem() {

        //when
        List<NaverItem> parsedItems = parser.parse(testResultElements);

        //then
        NaverItem parsedItem = parsedItems.get(0);
        /**Check required fields are filled**/
        assertThat(parsedItem.getLink()).isNotEmpty();
        assertThat(parsedItem.getCafeName()).isNotEmpty();
        assertThat(parsedItem.getDescription()).isNotEmpty();
        assertThat(parsedItem.getTitle()).isNotEmpty();
    }
}