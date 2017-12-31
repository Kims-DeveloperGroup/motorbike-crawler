package com.devoo.kim.service;

import com.devoo.kim.crawler.exception.NaverApiRequestException;
import com.devoo.kim.crawler.naver.NaverCafeSearchCrawler;
import com.devoo.kim.query.NaverQueryCreator;
import com.devoo.kim.repository.naver.NaverItemRepository;
import com.devoo.kim.service.exception.CrawlingFailureException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NaverCrawlingServiceTest {
    @InjectMocks
    private NaverCrawlingService naverCrawlingService;

    @Mock
    private NaverCafeSearchCrawler naverCafeSearchCrawler;

    @Mock
    private NaverItemRepository naverItemRepository;

    @Mock
    private NaverQueryCreator queryCreator;

    @Test
    public void shouldItemsBeSearchedAndUpdatedAsManyTimesAsCountOfQueries() throws NaverApiRequestException, CrawlingFailureException, IOException {
        //GIVEN
        int pageLimit = 3;
        List<String> mockQueries = Arrays.asList("query1", "query2");
        when(queryCreator.getQueries()).thenReturn(mockQueries);
        when(naverCafeSearchCrawler.search(anyString(), eq(pageLimit), anyInt()))
                .thenReturn(new ArrayList<>());
        //WHEN
        naverCrawlingService.updateSaleItems(pageLimit);
        //THEN
        verify(naverCafeSearchCrawler, times(mockQueries.size())).search(anyString(), eq(pageLimit), anyInt());
        verify(naverItemRepository, times(mockQueries.size())).save(anyCollection());
    }
}