package com.devoo.kim.crawler.naver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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
     * Asynchronously requests documents of result pages for a query as the number of page limit.
     *
     * @param query
     * @param pageLimit
     * @return
     */
    public Flux<Mono<String>> getDocuments(String query, int pageLimit) {
        AtomicInteger pageNumber = new AtomicInteger(1);
        return Flux.fromStream((() -> Stream.generate(() ->
                getDocument(query, pageNumber.getAndIncrement())).limit(pageLimit)));
    }

    /**
     * Asynchronously requests a document of search result page in a given page.
     *
     * @param query
     * @param pageNumber the page number of search pages (page number begins with 1)
     * @return Mono<String>
     * @throws UnsupportedEncodingException
     */
    public Mono<String> getDocument(String query, Integer pageNumber) {
        try {
            String targetURL = buildUrlForOnlyQueryEncoded(query, pageNumber);
            log.debug("Get doc: {}", targetURL);
            WebClient.ResponseSpec response = webClient.get()
                    .uri(targetURL)
                    .header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE)
                    .acceptCharset(Charset.forName("UTF8"))
                    .retrieve();
            return response.bodyToMono(String.class);
        } catch (UnsupportedEncodingException e) {
            log.warn("Exception getDocument: {}", e);
            return Mono.empty();
        }
    }

    /**
     * @param query      (Non-encoded string required, an encoded string is not recognized as same as decoded string)
     * @param pageNumber
     * @return
     * @throws UnsupportedEncodingException
     */
    private String buildUrlForOnlyQueryEncoded(String query, Integer pageNumber) throws UnsupportedEncodingException {
        String fragment = urlFragmentFormatter.format(new String[]{query, pageNumber.toString()});
        return UriComponentsBuilder.fromHttpUrl(cafeSearchRootUrl)
                .queryParam("query", query)
                .queryParam("page", pageNumber)
                .fragment(fragment)
                .build(false)
                .toUriString();
    }
}
