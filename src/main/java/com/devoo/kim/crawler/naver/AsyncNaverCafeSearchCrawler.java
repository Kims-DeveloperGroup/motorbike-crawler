package com.devoo.kim.crawler.naver;

import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.parser.NaverSearchResultDocumentParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;

@Component
@Slf4j
public class AsyncNaverCafeSearchCrawler {
    private static final String FRAGMENT_PATTERN = "%7B\"query\":\"{0}\"%7D";
    private static final MessageFormat urlFragmentFormatter = new MessageFormat(FRAGMENT_PATTERN);

    private final WebClient webClient;
    private NaverSearchResultDocumentParser naverSearchResultDocumentParser;
    private String cafeSearchRootUrl;

    @Autowired
    public AsyncNaverCafeSearchCrawler(@Value("${external.naver.cafeSearch.rootUrl}") String cafeSearchRootUrl,
                                       NaverSearchResultDocumentParser naverSearchResultDocumentParser) {
        this.cafeSearchRootUrl = cafeSearchRootUrl;
        this.naverSearchResultDocumentParser = naverSearchResultDocumentParser;
        webClient = WebClient.create();
    }

    public List<NaverItem> search(String query, int pageLimit) {
        List<NaverItem> resultItems = null;

        for (int currentPageNumber = 1; currentPageNumber < pageLimit; currentPageNumber++) {

        }

        return resultItems;
    }

    /**
     * Gets a document of search result page in a given page
     *
     * @param query
     * @param pageNumber the page number of search pages
     * @return a raw document of page
     * @throws UnsupportedEncodingException
     */
    public Mono<String> getDocument(String query, Integer pageNumber) throws UnsupportedEncodingException {
        String targetURL = buildUrlForOnlyQueryEncoded(query, pageNumber);
        WebClient.ResponseSpec response = webClient.get()
                .uri(targetURL)
                .header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE)
                .acceptCharset(Charset.forName("UTF8"))
                .retrieve();
        return response.bodyToMono(String.class);
    }

    private String buildUrlForOnlyQueryEncoded(String query, Integer pageNumber) throws UnsupportedEncodingException {
        String encodedQuery = URLEncoder.encode(query, "UTF8");
        String fragment = urlFragmentFormatter.format(new String[]{encodedQuery, pageNumber.toString()});
        return UriComponentsBuilder.fromHttpUrl(cafeSearchRootUrl)
                .queryParam("query", encodedQuery)
                .queryParam("page", pageNumber)
                .fragment(fragment)
                .build(false)
                .toUriString();
    }
}
