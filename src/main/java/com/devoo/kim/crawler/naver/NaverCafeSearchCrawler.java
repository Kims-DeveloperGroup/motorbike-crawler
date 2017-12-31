package com.devoo.kim.crawler.naver;

import com.devoo.kim.crawler.exception.NaverApiRequestException;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.parser.NaverSearchResultDocumentParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
    private static final String FRAGMENT_PATTERN = "\"query\":\"{0}\",\"sortBy\":0,\"period\":[],\"menuType\":[0],\"searchBy\":0,\"duplicate\":false,\"inCafe\":\"\",\"withOutCafe\":\"\",\"includeAll\":\"\",\"exclude\":\"\",\"include\":\"\",\"exact\":\"\",\"page\":{1},\"escrow\":\"\",\"onSale\":\"\"";
    private static final MessageFormat urlFragmentFormatter = new MessageFormat(FRAGMENT_PATTERN);
    public static final int PAGE_SIZE = 10;
    public static final String ID_RESULT_ELEMENT = "ArticleSearchResultArea";

    private final NaverSearchResultDocumentParser resultDocumentParser;

    @Autowired
    public NaverCafeSearchCrawler(
            NaverSearchResultDocumentParser resultDocumentParser,
            @Value("${external.naver.cafeSearch.rootUrl}") String cafeSearchRootUrl) {
        this.resultDocumentParser = resultDocumentParser;
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
        return resultDocumentParser.parse(resultDocuments, query);
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
        return Jsoup.connect(url).header("charset", "UTF8").get();
    }

    /**
     * Builds a fetching url of a search result page (* only the value of query param should be encoded.)
     * @param query
     * @param pageNumber
     * @return
     * @throws UnsupportedEncodingException
     */
    private String buildUrlForOnlyQueryEncoded(String query, Integer pageNumber) throws UnsupportedEncodingException {
        String encodedQuery = URLEncoder.encode(query, "UTF8");
        String fragment = "#{" + urlFragmentFormatter.format(new String[]{encodedQuery, pageNumber.toString()}) + "}";
        return UriComponentsBuilder.fromHttpUrl(cafeSearchRootUrl)
                .queryParam("query", encodedQuery)
                .queryParam("page", pageNumber)
                .fragment(fragment).build(false).toUriString();
    }
}