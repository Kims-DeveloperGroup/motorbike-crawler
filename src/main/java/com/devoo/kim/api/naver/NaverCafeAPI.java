package com.devoo.kim.api.naver;

import com.devoo.kim.api.exception.NaverApiRequestException;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.domain.naver.NaverSearchItems;
import com.devoo.kim.domain.naver.NaverSearchMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by rikim on 2017. 7. 16..
 */
@Deprecated
@Service
@Slf4j
public class NaverCafeAPI {
    private static final String CLIENT_ID_HEADER = "X-Naver-Client-Id";
    private static final String CLIENT_SECRET_HEADER = "X-Naver-Client-Secret";
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${external.naver.cafe.url}")
    private String cafeApiUrl;

    @Value("${external.naver.cafe.clientId}")
    private String clientId;

    @Value("${external.naver.cafe.clientSecret}")
    private String clientSeceret;

    private String SIMILARITY_ORDER = "sim";
    private String DATETIME_ORDER = "date";

    public static int pageSize;

    public NaverCafeAPI(@Value("${external.naver.pagination.size}") int pageSize) {
        NaverCafeAPI.pageSize = pageSize;
    }

    /**
     * Search items in Naver cafe
     * @param query
     * @param pageLimit  the limit number of pages to be retrieved. (100 items in a page)
     * @param startPageNumber the start page number of items to get (beginning with 0)
     * @return
     */
    public List<NaverItem> search(String query, int pageLimit, int startPageNumber) throws NaverApiRequestException {
        List<NaverItem> items = new ArrayList<>();
        NaverSearchMetadata metadata = getSearchMetadata(query);
        long lastPage = metadata.getLastPageNumber(pageLimit);
        for (int currentPage = startPageNumber; currentPage <= lastPage; currentPage++) {
            List<NaverItem> itemsInPage = getItemsInPage(query, currentPage);
            items.addAll(itemsInPage);
        }
        return items;
    }

    /**
     * Get CafeItems in a given pageNumber
     * @param query
     * @param pageNumber >= 0
     * @return
     */
    private List<NaverItem> getItemsInPage(String query, int pageNumber) throws NaverApiRequestException {
        log.debug("Crawling items in page {}", pageNumber);
        int startItemNumber = pageNumber == 0 ? 1 : pageSize * pageNumber;
        NaverItem[] items = doRequest(query, SIMILARITY_ORDER, pageSize, startItemNumber)
                .getBody().getNaverItems();
        return Arrays.asList(items);
    }

    /**
     * Gets search metadata for a given query. Search metadata includes info for item-traversing and pagination.
     * @param query search input
     * @return search metadata of naver cafe
     */
    private NaverSearchMetadata getSearchMetadata(String query) throws NaverApiRequestException {
        NaverSearchMetadata metadata = doRequest(query).getBody();
        log.debug("Total items: {} for {}", metadata.getTotalItems(), query);
        return metadata;
    }

    /**
     * Send a requests to search api of Naver cafe
     * @param query query input to search
     * @param sort sorting method, order
     * @param countOfResult limit count of items from search result (default =10, max = 100)
     * @param startItemNumber startItemNumber start index of items in search result (default = 1, max = 1000).
     * @return
     */
    private ResponseEntity<NaverSearchItems> doRequest(String query, String sort, int countOfResult, int startItemNumber) throws NaverApiRequestException {
        String encodedQuery = null;
        try {
            encodedQuery = URLEncoder.encode(query, "UTF8");
        } catch (UnsupportedEncodingException e) {
            throw new NaverApiRequestException();
        }
        URI url = UriComponentsBuilder.fromHttpUrl(cafeApiUrl)
                .queryParam("query", encodedQuery)
                .queryParam("sort", sort)
                .queryParam("display", countOfResult)
                .queryParam("start", startItemNumber).build().toUri();
        return restTemplate.exchange(url, HttpMethod.GET, header(), NaverSearchItems.class);
    }

    private ResponseEntity<NaverSearchItems> doRequest(String query) throws NaverApiRequestException {
        return doRequest(query, SIMILARITY_ORDER, 1, 1);
    }

    /**
     * creates request header with authentication.
     * @return http entities with auth
     */
    private HttpEntity<Void> header() {
        MultiValueMap<String, String> auth = new LinkedMultiValueMap<>();
        auth.add(CLIENT_ID_HEADER, clientId);
        auth.add(CLIENT_SECRET_HEADER, clientSeceret);
        return new HttpEntity<>(auth);
    }
}
