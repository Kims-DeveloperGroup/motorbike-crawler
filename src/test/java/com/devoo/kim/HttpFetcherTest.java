package com.devoo.kim;

import com.devoo.kim.config.HttpFetcherConfig;
import crawlercommons.fetcher.BaseFetchException;
import crawlercommons.fetcher.FetchedResult;
import crawlercommons.fetcher.http.BaseHttpFetcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by rikim on 2017. 7. 15..
 */
@RunWith(SpringRunner.class)
@SpringBootTest (classes = {HttpFetcherConfig.class})
public class HttpFetcherTest {
    @Autowired
    BaseHttpFetcher httpFetcher;
    @Test
    public void shouldCrawlTaskGetDocumentFromWebsite() throws BaseFetchException {
        String targetUrl;
        GIVEN: {
            targetUrl = "http://www.naver.com";
        }
        FetchedResult result;
        WHEN: {
            result = httpFetcher.get(targetUrl);
        }

        THEN: {
            assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        }
    }
}