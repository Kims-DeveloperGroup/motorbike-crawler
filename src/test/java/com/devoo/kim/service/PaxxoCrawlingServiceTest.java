package com.devoo.kim.service;

import com.devoo.kim.api.passo.PaxxoApiClient;
import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.domain.paxxo.PaxxoMakerIndices;
import com.devoo.kim.domain.paxxo.PaxxoSearchResult;
import com.devoo.kim.repository.PaxxoIndicesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rikim on 2017. 8. 14..
 */
@RunWith(MockitoJUnitRunner.class)
public class PaxxoCrawlingServiceTest {
    @InjectMocks
    private PaxxoCrawlingService paxxoCrawlingService;

    @Mock
    private PaxxoApiClient paxxoApiClient;

    @Mock
    private PaxxoIndicesRepository paxxoIndicesRepository;

    @Mock
    private PaxxoMakerIndices makerIndices;

    Maker[] mockMakers = {new Maker()};

    @Test
    public void shouldMakerIndicesBeUpdated() {
        GIVEN:
        {
            when(paxxoApiClient.getMakerIndices()).thenReturn(makerIndices);
            when(makerIndices.getMakers()).thenReturn(Arrays.asList(mockMakers));
            doNothing().when(paxxoIndicesRepository).save(anyCollectionOf(Maker.class));
        }
        WHEN:
        {
            paxxoCrawlingService.updatePaxxoMakerIndices();
        }

        THEN:
        {
            //Maker Indices Repository should be updated.
        }
    }

    @Test
    public void shouldSaveAndUpdateItems() {

        PaxxoSearchResult searchResult;
        WHEN:
        {
            paxxoCrawlingService.updateItems();
            doNothing().when(paxxoIndicesRepository).saveItems(any());
        }

        THEN:
        {
            verify(paxxoIndicesRepository).saveItems(any());
        }
    }
}
