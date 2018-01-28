package com.devoo.kim.crawler.naver;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.MessageFormat;

@Component
@Slf4j
public class AsyncNaverCafeSearchCrawler {
    private static final String FRAGMENT_PATTERN = "%7B\"query\":\"{0}\"%7D";
    private static final MessageFormat urlFragmentFormatter = new MessageFormat(FRAGMENT_PATTERN);

    private final WebClient webClient;
    private String cafeSearchRootUrl;

    @Autowired
    public AsyncNaverCafeSearchCrawler(@Value("${external.naver.cafeSearch.rootUrl}") String cafeSearchRootUrl) {
        this.cafeSearchRootUrl = cafeSearchRootUrl;
        webClient = WebClient.create();
    }

    /**
     * Gets a document of search result page in a given page
     *
     * @param query
     * @param pageNumber the page number of search pages
     * @return a raw document of page
     * @throws UnsupportedEncodingException
     */
    public Document getDocument(String query, Integer pageNumber) throws UnsupportedEncodingException {
        String targetURL = buildUrlForOnlyQueryEncoded(query, pageNumber);
        WebClient.ResponseSpec response = webClient.get()
                .uri(targetURL)
                .header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE)
                .acceptCharset(Charset.forName("UTF8"))
                .retrieve();
        return Jsoup.parse(response.bodyToMono(String.class).block());
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
