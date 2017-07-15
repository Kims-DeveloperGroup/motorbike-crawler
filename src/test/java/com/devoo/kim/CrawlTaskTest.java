package com.devoo.kim;

import crawlercommons.fetcher.BaseFetchException;
import crawlercommons.fetcher.FetchedResult;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;

/**
 * Created by rikim on 2017. 7. 15..
 */
@SpringBootTest
public class CrawlTaskTest {

    @Test
    public void shouldCrawlTaskGetDocumentFromWebsite() throws BaseFetchException {
        String targetUrl;
        GIVEN: {
            targetUrl = "http://www.naver.com";
        }
        FetchedResult result;
        WHEN: {
            CrawlTask crawlTask = new CrawlTask(targetUrl);
            result = crawlTask.crawl();
        }

        THEN: {
            assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        }
    }
}