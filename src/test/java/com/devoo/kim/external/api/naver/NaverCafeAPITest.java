package com.devoo.kim.external.api.naver;

import javafx.beans.binding.When;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by rikim on 2017. 7. 17..
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverCafeAPITest {
    @Autowired
    NaverCafeAPI naverCafeAPI;

    @Test
    public void shouldResponseCodeBe200WhenSendingRequet() {
        String query;
        GIVEN: {
            query = "naver";
        }

        ResponseEntity<String> response;
        WHEN: {
            response = naverCafeAPI.search("naver", 1);
        }

        THEN: {
            assertEquals(response.getStatusCode(), HttpStatus.OK);
        }
    }
}