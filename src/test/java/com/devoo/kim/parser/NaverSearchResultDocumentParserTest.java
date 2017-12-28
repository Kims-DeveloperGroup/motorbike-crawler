package com.devoo.kim.parser;

import com.devoo.kim.api.exception.NaverApiRequestException;
import com.devoo.kim.domain.naver.NaverItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class NaverSearchResultDocumentParserTest {
    private static final String TEST_URL
            = "https://section.cafe.naver.com/ArticleSearch.nhn?query=125cc 팝니다";
    private static Document testResultPageDocument;

    private NaverSearchResultDocumentParser parser = new NaverSearchResultDocumentParser();

    @BeforeClass
    public static void setUpTestData() throws NaverApiRequestException {
        testResultPageDocument = getTestDocument("query");
    }

    @Test
    public void shouldBeGivenRawElementsParsedToNaverItem() {

        //when
        List<NaverItem> parsedItems = parser.parse(testResultPageDocument);

        //then
        NaverItem parsedItem = parsedItems.get(0);
        /**Check required fields are filled**/
        assertThat(parsedItem.getLink()).isNotEmpty();
        assertThat(parsedItem.getCafeName()).isNotEmpty();
        assertThat(parsedItem.getDescription()).isNotEmpty();
        assertThat(parsedItem.getTitle()).isNotEmpty();
    }

    private static Document getTestDocument(String query) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(TEST_URL, String.class, query); // TODO: 2017. 12. 28. java.lang.IllegalArgumentException: Not enough variable values available to expand '"query"'
        return Jsoup.parse(response.getBody());
    }
}