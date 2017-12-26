package com.devoo.kim.service;

import com.devoo.kim.api.exception.NaverApiRequestException;
import com.devoo.kim.api.naver.NaverCafeAPI;
import com.devoo.kim.repository.naver.NaverItemRepository;
import com.devoo.kim.service.exception.CrawlingFailureException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

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
    private NaverCafeAPI naverCafeAPI;

    @Mock
    private NaverItemRepository naverItemRepository;

    @Test
    public void shouldMotorBikeSaleItemsBeUpdated() throws NaverApiRequestException, CrawlingFailureException {
        //GIVEN
        int pageLimit = 3;
        when(naverCafeAPI.search(anyString(), eq(pageLimit), anyInt()))
                .thenReturn(new ArrayList<>());
        //WHEN
        naverCrawlingService.updateSaleItems(pageLimit);
        //THEN
        verify(naverCafeAPI, times(1)).search(anyString(), eq(pageLimit), anyInt());
        verify(naverItemRepository, times(1)).save(anyCollection());
    }
}