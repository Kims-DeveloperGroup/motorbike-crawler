package com.devoo.kim.crawl;

import com.devoo.kim.util.FetcherUrls;
import crawlercommons.fetcher.BaseFetchException;
import crawlercommons.fetcher.FetchedResult;
import crawlercommons.fetcher.http.BaseHttpFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rikim on 2017. 7. 15..
 */
@Services
public class FetcherRunner {
    @Autowired
    BaseHttpFetcher httpFetcher;

    @Autowired
    FetcherUrls fetcherUrls;

    public void run() throws IOException, BaseFetchException {
        List<String> targetUrls = fetcherUrls.getAll();

        List<FetchedResult> results = new ArrayList<>();
        for (String url : targetUrls) {
            results.add(httpFetcher.get(url));
        }
    }
}
