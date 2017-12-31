package com.devoo.kim.service;

import com.devoo.kim.crawler.passo.PaxxoSaleItemCrawler;
import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.domain.paxxo.PaxxoMakerIndices;
import com.devoo.kim.repository.PaxxoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.*;

/**
 * Created by rikim on 2017. 8. 14..
 */
@RunWith(MockitoJUnitRunner.class)
public class PaxxoCrawlingServiceTest {
    @InjectMocks
    private PaxxoCrawlingService paxxoCrawlingService;

    @Mock
    private PaxxoSaleItemCrawler paxxoSaleItemCrawler;

    @Mock
    private PaxxoRepository paxxoRepository;

    @Mock
    private PaxxoMakerIndices makerIndices;

    Maker[] mockMakers = {new Maker()};

    @Test
    public void shouldMakerIndicesBeUpdated() {
        GIVEN:
        {
            when(paxxoSaleItemCrawler.getMakerIndices()).thenReturn(makerIndices);
            when(makerIndices.getMakers()).thenReturn(Arrays.asList(mockMakers));
            doNothing().when(paxxoRepository).saveMakerIndices(anyCollectionOf(Maker.class));
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
    public void shouldSaveAndUpdateItems() throws JAXBException {
        List<PaxxoItem> items = new ArrayList<>();
        int pageLimit = 3;
        GIVEN:
        {
            items.add(new PaxxoItem());
            when(paxxoSaleItemCrawler.getItems(pageLimit)).thenReturn(items);
            doNothing().when(paxxoRepository).saveItems(any());
        }

        int updated = 0;
        WHEN:
        {
            updated = paxxoCrawlingService.updateItems(pageLimit);
        }

        THEN:
        {
            Assertions.assertThat(updated).isEqualTo(items.size());
            verify(paxxoRepository).saveItems(any());
        }
    }
}
