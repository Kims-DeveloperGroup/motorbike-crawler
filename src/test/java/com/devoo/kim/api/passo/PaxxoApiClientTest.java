package com.devoo.kim.api.passo;

import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.domain.paxxo.PaxxoMakerIndices;
import com.devoo.kim.domain.paxxo.PaxxoSearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;

import java.util.List;

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
            result = paxxoApiClient.query(maker, model, 0);
        }

        THEN: {
            assertNotNull(result);
        }
    }

    @Test
    public void shouldRetrieveAllItemsWhenNoSearchInputIsGiven() throws JAXBException {

        List<PaxxoItem> result;
        WHEN: {
            result = paxxoApiClient.searchAll(2);
        }

        THEN: {
            assertNotNull(result);
        }
    }

    @Test
    public void shouldRetrieveMakerIndex() {
        GIVEN: {}

        PaxxoMakerIndices index;
        WHEN: {
           index = paxxoApiClient.getMakerIndices();
        }

        THEN: {
            assertNotNull(index);
        }
    }
}