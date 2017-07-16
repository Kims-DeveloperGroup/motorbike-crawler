package com.devoo.kim.crawl;

import com.devoo.kim.storage.FetchedResultRepository;
import com.devoo.kim.util.FetcherUrls;
import crawlercommons.fetcher.FetchedResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by rikim on 2017. 7. 15..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FetcherRunnerTest {
    @Autowired
    FetcherRunner fetcherRunner;

    @Autowired
    FetcherUrls fetcherUrls;

    @Autowired
    FetchedResultRepository fetchedResultRepository;

    @Test
    public void shouldFetchFromUrlsOfFetchUrlFile() throws Exception {
        List<String> fetchingUrl;
        GIVEN:
        {
            fetchingUrl = fetcherUrls.getAll();
        }

        WHEN:
        {
            fetcherRunner.run();
        }


        List<FetchedResult> results;
        THEH:
        {
            results = fetchedResultRepository.findAll();
            assertEquals(results.size(), fetchingUrl.size());

        }
    }

}