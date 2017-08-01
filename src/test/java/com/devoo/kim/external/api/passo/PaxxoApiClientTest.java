package com.devoo.kim.external.api.passo;

import com.devoo.kim.data.paxxo.PaxxoDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by rikim on 2017. 7. 30..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PaxxoApiClient.class})
@ActiveProfiles("test")
public class PaxxoApiClientTest {
    @Autowired
    PaxxoApiClient requestClient;

    @Test
    public void shouldRetrieveSearchResultData() {
        String maker;
        String model;
        GIVE: {
            maker = "honda";
            model = "cbr125r";
        }

        PaxxoDataSet result;
        WHEN: {
            result = requestClient.search(maker, model);
        }

        THEN: {
            assertNotNull(result);
        }
    }

}