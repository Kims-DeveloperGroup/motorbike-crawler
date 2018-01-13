package com.devoo.kim.domain.paxxo;

import com.devoo.kim.config.paxxo.PaxxoHttpClientConfig;
import com.devoo.kim.crawler.passo.PaxxoSaleItemCrawler;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PaxxoSaleItemCrawler.class, PaxxoHttpClientConfig.class})
public class PaxxoItemTest {

    @Autowired
    PaxxoSaleItemCrawler paxxoSaleItemCrawler;

    @Value("${external.paxxo.item-url-pattern}")
    private String itemUrlPattern;

    @Test
    public void should_item_link_respond_200_OK() throws Exception {
        //GIVEN
        MessageFormat urlFormatter = new MessageFormat(itemUrlPattern);
        RestTemplate restTemplate = new RestTemplate();
        PaxxoItem item = paxxoSaleItemCrawler.getItems(0, 1).get(0);

        //WHEN
        item.generateUrl(urlFormatter);
        String link = item.getUrl();
        ResponseEntity<Void> response = restTemplate.getForEntity(link, Void.class);
        //THEN
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}