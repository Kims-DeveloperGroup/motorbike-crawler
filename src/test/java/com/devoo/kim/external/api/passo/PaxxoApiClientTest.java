package com.devoo.kim.external.api.passo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by rikim on 2017. 7. 30..
 */
public class PassoApiClientTest {
    @Autowired
    PassoApiClient requestClient;

    @Test
    public void shouldRetrieveSearchResultData() {
        GIVEN: {
            requestClient.search("model", "");
        }
    }

}