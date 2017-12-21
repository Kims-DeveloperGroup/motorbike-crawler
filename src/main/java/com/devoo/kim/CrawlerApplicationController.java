package com.devoo.kim;

import com.devoo.kim.service.PaxxoCrawlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerApplicationController {
    @Autowired
    private PaxxoCrawlingService paxxoCrawlingService;


    @PutMapping("/paxxo/sale-items")
    public void updatePaxxoSalesItem() {
        paxxoCrawlingService.updateItems();
    }
}
