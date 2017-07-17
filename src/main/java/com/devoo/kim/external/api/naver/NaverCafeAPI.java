package com.devoo.kim.external.api.naver;

import com.devoo.kim.data.FetchedResultWrapper;
import crawlercommons.fetcher.FetchedResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * Created by rikim on 2017. 7. 16..
 */
@Service
public class NaverCafeAPI {
    private final String CLIENT_ID_HEADER = "X-Naver-Client-Id";
    private final String CLIENT_SECRET_HEADER = "X-Naver-Client-Secret";
    private final String API_URL = "https://openapi.naver.com/v1/search/cafearticle.json";

    private String clientId;
    private String clientSeceret;

    private void doRequest(String query, String sort, int countfResult, int start) {
        URI url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("query", query)
                .queryParam("sort", sort)
                .queryParam("display", countfResult)
                .queryParam("start", start).build().toUri();

        HttpEntity<String> requestEntity = new HttpEntity(headers());


        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.exchange(url, HttpMethod.GET, (HttpEntity<?>) requestEntity, String.class);

    }

    private MultiValueMap<String, Object> headers() {
        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<>();
        headers.add(CLIENT_ID_HEADER, clientId);
        headers.add(CLIENT_SECRET_HEADER, clientSeceret);
        return headers;
    }
}
