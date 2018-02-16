package com.devoo.kim.service;

import com.devoo.kim.crawler.exception.NaverApiRequestException;
import com.devoo.kim.crawler.naver.AsyncNaverCafeSearchCrawler;
import com.devoo.kim.query.NaverQueryCreator;
import com.devoo.kim.service.exception.CrawlingFailureException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NaverCrawlingServiceTest {
    @InjectMocks
    private NaverCrawlingService naverCrawlingService;

    @Mock
    private AsyncNaverCafeSearchCrawler asyncNaverCafeSearchCrawler;

    @Mock
    private NaverQueryCreator queryCreator;

    @Test
    public void shouldItemsBeSearchedAndUpdatedAsManyTimesAsCountOfQueries() throws NaverApiRequestException, CrawlingFailureException, IOException, InterruptedException {
        //GIVEN
        int pageLimit = 3;
        List<String> mockQueries = Arrays.asList("query1", "query2");
        when(queryCreator.getQueries()).thenReturn(mockQueries);

        when(asyncNaverCafeSearchCrawler.getDocuments(anyString(), eq(pageLimit)))
                .thenReturn(Flux.empty());
        when(asyncNaverCafeSearchCrawler.getDocuments("query", pageLimit))
                .thenReturn(Flux.empty());

        //WHEN
        naverCrawlingService.updateSaleItems(pageLimit);

        //THEN
        verify(asyncNaverCafeSearchCrawler, times(mockQueries.size())).getDocuments(anyString(), eq(pageLimit));
    }
}