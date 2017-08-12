package com.devoo.kim.external.api.passo;

import com.devoo.kim.domain.paxxo.PaxxoDataIndex;
import com.devoo.kim.domain.paxxo.PaxxoDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.*;

/**
 * Created by rikim on 2017. 7. 30..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PaxxoApiClient.class})
@ActiveProfiles("test")
public class PaxxoApiClientTest {
    @Autowired
    private PaxxoApiClient paxxoApiClient;

    @Test
    public void shouldRetrieveSearchResultData() throws JAXBException {
        String maker;
        String model;
        GIVE: {
            maker = "34";
            model = "366";
        }

        PaxxoDataSet result;
        WHEN: {
            result = paxxoApiClient.search(maker, model);
        }

        THEN: {
            assertNotNull(result);
        }
    }

    @Test
    public void shouldRetrieveCountryAndMakerIndex() {
        GIVEN: {}

        PaxxoDataIndex index;
        WHEN: {
           index = paxxoApiClient.getCountryAndMakerIndex();
        }

        THEN: {
            assertNotNull(index);
        }
    }
}