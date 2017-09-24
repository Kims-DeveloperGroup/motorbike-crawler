package com.devoo.kim.api.passo;

import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.domain.paxxo.PaxxoItemMetadata;
import com.devoo.kim.domain.paxxo.PaxxoMakerIndices;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
/**
 * Created by rikim on 2017. 7. 30..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PaxxoApiClient.class})
@ActiveProfiles("test")
public class PaxxoApiClientIT {
    @Autowired
    private PaxxoApiClient paxxoApiClient;

    @Value("${external.paxxo.pagination.size}")
    private int pageSize;

    @Test
    public void shouldRetrieveSearchResultDataByMakerAndModel() throws JAXBException {
        String makerInput;
        String modelInput;
        GIVE: {
            makerInput = "34";
            modelInput = "366";
        }

        PaxxoItemMetadata result;
        WHEN: {
            result = paxxoApiClient.getItemMetadata(makerInput, modelInput);
        }

        THEN: {
            assertNotNull(result);
        }
    }

    @Test
    public void shouldRetrieveLimitedNumberOfItemsWhenNoSearchInputAndSpecificLimitNumberIsGiven() throws JAXBException {

        int pageLimit;
        GIVEN:
        {
            pageLimit = 2;
        }

        List<PaxxoItem> result;
        WHEN: {
<<<<<<< HEAD
            result = paxxoApiClient.getItems(pageLimit);
=======
            result = paxxoApiClient.getItems(limit);
>>>>>>> url-generation
        }
        int expectedItems = this.pageSize * pageLimit;
        THEN: {
            Assertions.assertThat(result.size()).isEqualTo(expectedItems);
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