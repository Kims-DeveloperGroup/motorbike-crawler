package com.devoo.kim.crawler.naver;

import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.parser.NaverSearchResultDocumentParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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
        List<NaverItem> resultItems = new LinkedList<>();
        AtomicInteger pageNumber = new AtomicInteger(1);
        Flux<Mono<String>> monoFlux = Flux.fromStream((() -> Stream.generate(() -> {
                    Mono<String> document = getDocument(query, pageNumber.getAndIncrement());
                    return document;
                }
        ).limit(pageLimit)));
        monoFlux.subscribe(document -> {
            document.subscribe(docString -> {
                List<NaverItem> items = naverSearchResultDocumentParser.parse(Jsoup.parse(docString), query);
                resultItems.addAll(items);
            });
        });
        return resultItems;
    }

    /**
     * Gets a document of search result page in a given page
     *
     * @param query
     * @param pageNumber the page number of search pages (page number begins with 1)
     * @return a raw document of page
     * @throws UnsupportedEncodingException
     */
    public Mono<String> getDocument(String query, Integer pageNumber) {
        try {
            String targetURL = buildUrlForOnlyQueryEncoded(query, pageNumber);
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
