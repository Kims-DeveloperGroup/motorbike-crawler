package com.devoo.kim;

import com.devoo.kim.service.PaxxoCrawlingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@Slf4j
public class CrawlerApplicationController {
    @Autowired
    private PaxxoCrawlingService paxxoCrawlingService;


    @PutMapping("/paxxo/sale-items")
    public void updatePaxxoSalesItem(@RequestParam(required = false) Integer pageLimit) {
        log.info("Crawling items from Paxxo...");
        Instant startTime = Instant.now();
        if (pageLimit == null) {
            pageLimit = PaxxoCrawlingService.MAX_PAGE_LIMIT;
        }
        paxxoCrawlingService.updateItems(pageLimit);
        Instant endTime = Instant.now();
        log.info("Crawling time: {} seconds.", Duration.between(startTime, endTime).toMillis() / 1000);
    }
}
