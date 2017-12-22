package com.devoo.kim;

import com.devoo.kim.service.PaxxoCrawlingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@Slf4j
public class CrawlerApplicationController {
    @Autowired
    private PaxxoCrawlingService paxxoCrawlingService;


    @PutMapping("/paxxo/sale-items")
    public void updatePaxxoSalesItem() {
        log.info("Crawling items from Paxxo...");
        Instant startTime = Instant.now();
        paxxoCrawlingService.updateItems();
        Instant endTime = Instant.now();
        log.info("Crawling time: {} seconds.", Duration.between(startTime, endTime).toMillis() / 1000);
    }
}
