package com.devoo.kim.external.api.passo;

import com.devoo.kim.domain.paxxo.PaxxoMakerIndices;
import com.devoo.kim.domain.paxxo.PaxxoSearchResult;
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
    public void shouldRetrieveSearchResultDataByMakerAndModel() throws JAXBException {
        String maker;
        String model;
        GIVE: {
            maker = "34";
            model = "366";
        }

        PaxxoSearchResult result;
        WHEN: {
            result = paxxoApiClient.search(maker, model);
        }

        THEN: {
            assertNotNull(result);
        }
    }

    @Test
    public void shouldRetrieveAllItemsByNoSearchInput() throws JAXBException {

        PaxxoSearchResult result;
        WHEN: {
            result = paxxoApiClient.searchAll();
        }

        THEN: {
            assertNotNull(result);
        }
    }

    @Test
    public void shouldRetrieveCountryAndMakerIndex() {
        GIVEN: {}

        PaxxoMakerIndices index;
        WHEN: {
           index = paxxoApiClient.getCountryAndMakerIndex();
        }

        THEN: {
            assertNotNull(index);
        }
    }
}