package com.devoo.kim.crawl;

import com.devoo.kim.storage.FetchedResultRepository;
import com.devoo.kim.storage.exception.FailToSaveFetchedResultException;
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
@Service
public class FetcherRunner {
    @Autowired
    private BaseHttpFetcher httpFetcher;

    @Autowired
    private FetcherUrls fetcherUrls;

    @Autowired
    private FetchedResultRepository fetchedResultRepository;

    public void run() throws IOException, BaseFetchException, FailToSaveFetchedResultException {
        List<String> targetUrls = fetcherUrls.getAll();

        List<FetchedResult> results = new ArrayList<>();
        for (String url : targetUrls) {
            results.add(httpFetcher.get(url));
        }
        fetchedResultRepository.save(results);
    }
}
