package com.devoo.kim.api.naver;

import com.devoo.kim.api.exception.NaverApiRequestException;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.parser.NaverSearchResultDocumentParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class NaverCafeSearchCrawler {
    private String cafeSearchRootUrl;
    private String fragmentPattern = "\"query\":\"{0}\",\"sortBy\":0,\"period\":[],\"menuType\":[0],\"searchBy\":0,\"duplicate\":false,\"inCafe\":\"\",\"withOutCafe\":\"\",\"includeAll\":\"\",\"exclude\":\"\",\"include\":\"\",\"exact\":\"\",\"page\":{1},\"escrow\":\"\",\"onSale\":\"\"";

    private static MessageFormat urlFragmentFormatter;
    private RestTemplate restTemplate;
    private final NaverSearchResultDocumentParser resultElementsParszer;

    public static final int PAGE_SIZE = 10;
    public static final String ID_RESULT_ELEMENT = "ArticleSearchResultArea";

    @Autowired
    public NaverCafeSearchCrawler(
            NaverSearchResultDocumentParser resultElementsParszer,
            @Value("${external.naver.cafeSearch.rootUrl}") String cafeSearchRootUrl
    ) {
        this.resultElementsParszer = resultElementsParszer;
        this.urlFragmentFormatter = new MessageFormat(fragmentPattern);
        this.restTemplate = new RestTemplate();
        this.cafeSearchRootUrl = cafeSearchRootUrl;

    }

    /**
     * Search for a given query and returns result items.
     *
     * @param query           query words
     * @param pageLimit       total count of pages to crawl
     * @param startPageNumber a page number of search result page
     * @return search result items from crawled pages (init number = 0)
     * @throws NaverApiRequestException
     */
    public List<NaverItem> search(String query, int pageLimit, int startPageNumber) throws NaverApiRequestException {
        List<Document> resultDocuments = new LinkedList<>();
        log.info("query: {} \n pageLimit: {} \n startPageNumber: {}", query, pageLimit, startPageNumber);
        for (int currentPageNumber = startPageNumber; currentPageNumber < pageLimit; currentPageNumber++) {
            log.debug("Crawling page {}", currentPageNumber);
            try {
                Document resultPageDocument = getDocuments(query, currentPageNumber+1);
                resultDocuments.add(resultPageDocument);
            } catch (IOException e) {
                log.warn("Fail to get Document from page {}", currentPageNumber);
            }
        }
        return resultElementsParszer.parse(resultDocuments);
    }

    /**
     * Fetches a document of a result page for a given query and a page number.
     * @param query
     * @param pageNumber of result page (not zero based, init number = 1)
     *                   naver paging begins with 1, but not 0
     * @return
     * @throws IOException
     */
    Document getDocuments(String query, Integer pageNumber) throws IOException {
        String url = buildUrlForOnlyQueryEncoded(query, pageNumber);
        log.debug("Fetching document from {}", url);
        return Jsoup.connect(url).get();
    }

    /**
     * Builds a fetching url of a search result page (* only the value of query param should be encoded.)
     *
     * @param query
     * @param pageNumber
     * @return
     * @throws UnsupportedEncodingException
     */
    private String buildUrlForOnlyQueryEncoded(String query, Integer pageNumber) throws UnsupportedEncodingException {
        String encodedQuery = URLEncoder.encode(query, "UTF8");
        String fragment = "#{" + urlFragmentFormatter.format(new String[]{encodedQuery, pageNumber.toString()}) + "}";
        String urlWithOnlyQueryStringEncoded = UriComponentsBuilder.fromHttpUrl(cafeSearchRootUrl)
                .queryParam("query", encodedQuery)
                .queryParam("page", pageNumber)
                .fragment(fragment).build(false).toUriString();
        return urlWithOnlyQueryStringEncoded;
    }
}