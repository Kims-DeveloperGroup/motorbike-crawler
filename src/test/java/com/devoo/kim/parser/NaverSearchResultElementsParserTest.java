package com.devoo.kim.parser;

import com.devoo.kim.api.exception.NaverApiRequestException;
import com.devoo.kim.api.naver.NaverCafeSearchCrawler;
import com.devoo.kim.domain.naver.NaverItem;
import org.assertj.core.api.Assertions;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;


public class NaverSearchResultElementsParserTest {

    private static NaverCafeSearchCrawler naverCafeSearchCrawler
            = new NaverCafeSearchCrawler("https://section.cafe.naver.com/ArticleSearch.nhn");
    private static Elements testResultElements;

    private NaverSearchResultElementsParser parser = new NaverSearchResultElementsParser();

    @BeforeClass
    public static void setUpTestData() throws NaverApiRequestException {
        testResultElements = naverCafeSearchCrawler.search("125cc 팝니다", 1, 0).get(0);
    }

    @Test
    public void shouldBeGivenRawElementsParsedToNaverItem() {

        //when
        List<NaverItem> parsedItems = parser.parse(testResultElements);
        //then
        Assertions.assertThat(parsedItems.get(0).getLink()).isNotEmpty();
    }
}