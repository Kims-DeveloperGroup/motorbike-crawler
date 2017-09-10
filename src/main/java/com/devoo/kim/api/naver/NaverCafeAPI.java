package com.devoo.kim.api.naver;

import com.sun.org.apache.regexp.internal.RE;
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


/**
 * Created by rikim on 2017. 7. 16..
 */
@Service
public class NaverCafeAPI {
    private final String CLIENT_ID_HEADER = "X-Naver-Client-Id";
    private final String CLIENT_SECRET_HEADER = "X-Naver-Client-Secret";
    private final String API_URL = "https://openapi.naver.com/v1/search/cafearticle.json";

    @Value("${external.naver.cafe.clientId}")
    private String clientId;
    @Value("${external.naver.cafe.clientSecret}")
    private String clientSeceret;
    private RestTemplate restTemplate = new RestTemplate();
    private String SIMILARITY_ORDER = "sim";

    /**
     * Search items in Naver cafe
     * @param query
     * @param countOfResult
     * @return
     */
    public ResponseEntity<String> search(String query, int countOfResult) {
        return doRequest(query, SIMILARITY_ORDER, countOfResult, 1);
    }

    /**
     * Send a requests to search api of Naver cafe
     * @param query query input to search
     * @param sort sorting method, order
     * @param countOfResult limit count of items from search result
     * @param start start index of items from result.
     * @return
     */
    private ResponseEntity<String> doRequest(String query, String sort, int countOfResult, int start) {
        URI url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("query", query)
                .queryParam("sort", sort)
                .queryParam("display", countOfResult)
                .queryParam("start", start).build().toUri();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, header(), String.class);
        return response;
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
