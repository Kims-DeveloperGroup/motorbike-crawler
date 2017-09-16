package com.devoo.kim.api.naver;

import com.devoo.kim.domain.naver.CafeItem;
import com.devoo.kim.domain.naver.NaverSearchItems;
import com.devoo.kim.domain.naver.NaverSearchMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by rikim on 2017. 7. 16..
 */
@Service
public class NaverCafeAPI {
    private final String CLIENT_ID_HEADER = "X-Naver-Client-Id";
    private final String CLIENT_SECRET_HEADER = "X-Naver-Client-Secret";
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${external.naver.cafe.url}")
    private String cafeApiUrl;

    @Value("${external.naver.cafe.clientId}")
    private String clientId;

    @Value("${external.naver.cafe.clientSecret}")
    private String clientSeceret;

    @Value("${external.naver.pagination.size}")
    public static int pageSize;

    private String SIMILARITY_ORDER = "sim";
    private String DATETIME_ORDER = "date";

    /**
     * Search items in Naver cafe
     * @param query
     * @param limitOfPage  limit of items to retrieve (100 items in a page)
     * @return
     */
    public List<CafeItem> search(String query, int limitOfPage) {
        List<CafeItem> items = new ArrayList<>();
        NaverSearchMetadata metadata = getSerachMetadata(query);
        long lastPage = metadata.getLastPage(limitOfPage)
        for (int page =0 ; page < lastPage; page++) {
            items.addAll(getItemsInPage(query, page));
        }
        return items;
    }

    /**
     * Get CafeItems in a given page
     * @param query
     * @param page
     * @return
     */
    private List<CafeItem> getItemsInPage(String query, int page) {
        CafeItem[] items = doRequest(query, SIMILARITY_ORDER, 100, 100*page + 1).getBody().getCafeItems();
        return Arrays.asList(items);
    }

    /**
     * Gets search metadata for a given query. Search metadata includes info for item-traversing and pagination.
     * @param query search input
     * @return search metadata of naver cafe
     */
    private NaverSearchMetadata getSerachMetadata(String query) {
        NaverSearchItems items = doRequest(query).getBody();
        return doRequest(query).getBody();
    }

    /**
     * Send a requests to search api of Naver cafe
     * @param query query input to search
     * @param sort sorting method, order
     * @param countOfResult limit count of items from search result (default =10, max = 100)
     * @param start start index of items from result (default = 1, max = 1000).
     * @return
     */
    private ResponseEntity<NaverSearchItems> doRequest(String query, String sort, int countOfResult, int start) {
        URI url = UriComponentsBuilder.fromHttpUrl(cafeApiUrl)
                .queryParam("query", query)
                .queryParam("sort", sort)
                .queryParam("display", countOfResult)
                .queryParam("start", start).build().toUri();
        return restTemplate.exchange(url, HttpMethod.GET, header(), NaverSearchItems.class);
    }

    private ResponseEntity<NaverSearchItems> doRequest(String query) {
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
        HttpEntity<Void> httpHeader = new HttpEntity<>(auth);
        return httpHeader;
    }
}
