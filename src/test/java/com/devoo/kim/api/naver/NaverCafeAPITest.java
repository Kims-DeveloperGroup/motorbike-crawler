package com.devoo.kim.api.naver;

import com.devoo.kim.domain.naver.NaverItem;
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
        int pageLimit;
        GIVEN:
        {
            pageLimit = 2;
            query = "naver";
        }
        List<NaverItem> items;
        WHEN:
        {
            items = naverCafeAPI.search(query, pageLimit, 0);
        }

        int expectedItems = this.pageSize * pageLimit;
        THEN:
        {
            Assertions.assertThat(items).size().isEqualTo(expectedItems);
        }
    }
}