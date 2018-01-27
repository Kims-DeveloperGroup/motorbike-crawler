package com.devoo.kim.crawler.naver;

import com.devoo.kim.crawler.exception.NaverApiRequestException;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.parser.NaverSearchResultDocumentParser;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AsyncNaverCafeSearchCrawler extends NaverCafeSearchCrawler {

    public AsyncNaverCafeSearchCrawler(NaverSearchResultDocumentParser resultDocumentParser, String cafeSearchRootUrl) {
        super(resultDocumentParser, cafeSearchRootUrl);
//        WebClient webClient = WebClient.builder();
    }

    @Override
    public List<NaverItem> search(String query, int pageLimit, int startPageNumber) throws NaverApiRequestException {
        return super.search(query, pageLimit, startPageNumber);
    }

    @Override
    Document getDocuments(String query, Integer pageNumber) throws IOException {
        return super.getDocuments(query, pageNumber);
    }
}
