package com.devoo.kim;

import crawlercommons.fetcher.BaseFetchException;
import crawlercommons.fetcher.FetchedResult;
import crawlercommons.fetcher.http.SimpleHttpFetcher;
import crawlercommons.fetcher.http.UserAgent;
import org.springframework.stereotype.Component;

/**
 * Created by rikim on 2017. 7. 15..
 */
@Component
public class CrawlTask{
    private static SimpleHttpFetcher httpFetcher = new SimpleHttpFetcher(new UserAgent("httpFetcher",null,null));
    private String targetUrl;

    public CrawlTask(String targetUrl) {
        this.targetUrl =targetUrl;
    }

    public FetchedResult crawl() throws BaseFetchException {
        return httpFetcher.fetch(targetUrl);
    }
}
