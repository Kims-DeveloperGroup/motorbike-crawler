package com.devoo.kim;

import com.devoo.kim.service.NaverCrawlingService;
import com.devoo.kim.service.PaxxoCrawlingService;
import com.devoo.kim.service.exception.CrawlingFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class CrawlerApplicationController {
    private PaxxoCrawlingService paxxoCrawlingService;
    private NaverCrawlingService naverCrawlingService;

    @Autowired
    public CrawlerApplicationController(PaxxoCrawlingService paxxoCrawlingService, NaverCrawlingService naverCrawlingService) {
        this.paxxoCrawlingService = paxxoCrawlingService;
        this.naverCrawlingService = naverCrawlingService;
    }

    @PutMapping("/paxxo/sale-items")
    public void updatePaxxoSalesItem(@RequestParam(required = false) Integer startPageNumber,
                                     @RequestParam(required = false) Integer pageLimit) {
        paxxoCrawlingService.updatePaxxoMakerIndices();
        log.info("Crawling items from Paxxo...");
        if (pageLimit == null) {
            pageLimit = PaxxoCrawlingService.MAX_PAGE_LIMIT;
        }
        if (startPageNumber == null) {
            startPageNumber = 0;
        }
        paxxoCrawlingService.updateItemsWithTaskExecutor(startPageNumber, pageLimit);
    }

    @PutMapping("/naver/sale-items")
    public void updateNaverSalesItem(@RequestParam(required = false) Integer pageLimit) throws CrawlingFailureException, IOException, InterruptedException {
        log.info("Crawling items from Naver...");
        if (pageLimit == null) {
            pageLimit = NaverCrawlingService.MAX_PAGE_LIMIT;
        }
        naverCrawlingService.updateSaleItems(pageLimit);
    }

    @PutMapping("/paxxo/models")
    public void updatePaxxoModels() {
        paxxoCrawlingService.updateModels();
    }
}
