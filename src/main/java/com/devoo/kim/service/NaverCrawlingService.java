package com.devoo.kim.service;

import com.devoo.kim.api.naver.NaverCafeSearchCrawler;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.query.NaverQueryCreator;
import com.devoo.kim.repository.naver.NaverItemRepository;
import com.devoo.kim.service.exception.CrawlingFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NaverCrawlingService {

    public static final Integer MAX_PAGE_LIMIT = 0;
    private String saleItemQuery = "naver";
    private NaverCafeSearchCrawler naverCafeSearchCrawler;
    private NaverItemRepository itemRepository;
    private NaverQueryCreator queryCreator;

    @Autowired
    public NaverCrawlingService(NaverCafeSearchCrawler naverCafeSearchCrawler,
                                NaverItemRepository itemRepository,
                                NaverQueryCreator queryCreator) {
        this.naverCafeSearchCrawler = naverCafeSearchCrawler;
        this.itemRepository = itemRepository;
        this.queryCreator = queryCreator;
    }

    public void updateSaleItems(int pageLimit) throws CrawlingFailureException {
        log.info("Crawling naver sale items...");
        List<NaverItem> searchedItems = null;
        try {
            saleItemQuery = queryCreator.getQuery();
            searchedItems = naverCafeSearchCrawler.search(saleItemQuery, pageLimit, 0);
        } catch (Exception e) {
            throw new CrawlingFailureException();
        }
        log.info("Saving searched {} items...", searchedItems.size());
        itemRepository.save(searchedItems);
        log.info("Naver sale item update completed.");
    }
}