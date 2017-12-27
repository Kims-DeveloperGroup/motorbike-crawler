package com.devoo.kim.service;

import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.service.exception.CrawlingFailureException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverCrawlingServiceIT {
    @Autowired
    private NaverCrawlingService naverCrawlingService;

    @Test
    public void shouldBeBATUMAsSaleItemCrawled_whenBATUMAspecificQueryIsGiven() throws CrawlingFailureException {
        //given
        int pageLimit = 11;

        //when
        List<NaverItem> batumaniaItems = naverCrawlingService.updateBatumaniaItems(pageLimit);

        //then
        NaverItem actualItem = batumaniaItems.get(0);
        Assertions.assertThat(actualItem.getCafeUrl()).contains("http://cafe.naver.com/bikecargogo");
    }
}