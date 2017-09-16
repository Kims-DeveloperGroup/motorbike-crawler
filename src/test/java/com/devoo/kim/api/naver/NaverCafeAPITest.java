package com.devoo.kim.api.naver;

import com.devoo.kim.domain.naver.CafeItem;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by rikim on 2017. 7. 17..
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NaverCafeAPI.class})
public class NaverCafeAPITest {
    @Autowired
    private NaverCafeAPI naverCafeAPI;

    @Value("${external.naver.pagination.size}")
    private int pageSize;

    @Test
    public void shouldItemsBeRetrievedAsMuchAsSizeOfPage() {
        String query;
        GIVEN:
        {
            query = "naver";
        }
        List<CafeItem> items;
        WHEN:
        {
            items = naverCafeAPI.search("naver", 1);
        }

        int expectedItems = this.pageSize;
        THEN:
        {
            Assertions.assertThat(items).size().isEqualTo(expectedItems);
        }
    }
}