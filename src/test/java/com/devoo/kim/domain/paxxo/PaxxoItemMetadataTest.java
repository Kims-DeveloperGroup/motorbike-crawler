package com.devoo.kim.domain.paxxo;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaxxoItemMetadataTest {
    @InjectMocks
    private PaxxoItemMetadata paxxoItemMetadata;

    @Mock
    private PaxxoPagination paxxoPagination;

    @Test
    public void shouldReturnGivenStartPageNumberPlusPageLimit_whenTotalPageCountIsBiggerThanStartPageNumberPlusGivenPageLimit() {
        //given
        int startPageNumber = 200;
        int pageLimit = 200;
        int lastPage = 500;
        int expectedLastPageNumber = startPageNumber + pageLimit - 1;
        when(paxxoPagination.getLastPage()).thenReturn(lastPage).thenReturn(lastPage);

        //when
        int actualLastPageNumber = paxxoItemMetadata.getLastPageNumber(startPageNumber, pageLimit);

        //then
        Assertions.assertThat(actualLastPageNumber).isEqualTo(expectedLastPageNumber);
    }

    @Test
    public void shouldJustReturnLastPageNumberFromItemMetaData_whenTotalPageCountIsSmallerThanStartPageNumberPlusGivenPageLimit() {
        //given
        int startPageNumber = 200;
        int pageLimit = 200;
        int lastPage = 300;
        int expectedLastPageNumber = lastPage;
        when(paxxoPagination.getLastPage()).thenReturn(lastPage).thenReturn(lastPage);

        //when
        int actualLastPageNumber = paxxoItemMetadata.getLastPageNumber(startPageNumber, pageLimit);

        //then
        Assertions.assertThat(actualLastPageNumber).isEqualTo(expectedLastPageNumber);
    }
}