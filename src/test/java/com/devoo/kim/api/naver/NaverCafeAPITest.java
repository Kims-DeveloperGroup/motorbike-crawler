package com.devoo.kim.api.naver;

import com.devoo.kim.domain.naver.CafeItem;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by rikim on 2017. 7. 17..
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverCafeAPITest {
    @Autowired
    private NaverCafeAPI naverCafeAPI;

    @Test
    public void shouldResponseCodeBe200WhenSendingRequet() {
        String query;
        GIVEN: {
            query = "naver";
        }
        List<CafeItem> items = new ArrayList<>();
        WHEN: {
            items = naverCafeAPI.search("naver", 1);
        }

        THEN: {
            Assertions.assertThat(items).size().isEqualTo(100);
        }
        // TODO: 2017. 9. 10. Latest Work ; Json Conversion error to Domain class
    }
}