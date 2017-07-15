package com.devoo.kim;

import com.devoo.kim.config.HttpFetcherConfig;
import crawlercommons.fetcher.BaseFetchException;
import crawlercommons.fetcher.FetchedResult;
import crawlercommons.fetcher.http.BaseHttpFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by rikim on 2017. 7. 15..
 */
@Component
public class CrawlTask{
    private String targetUrl;

    @Autowired
    BaseHttpFetcher httpFetcher;
    public CrawlTask(String targetUrl) {
        this.targetUrl =targetUrl;
    }

    public FetchedResult crawl() throws BaseFetchException {
        return httpFetcher.get(targetUrl);
    }
}
