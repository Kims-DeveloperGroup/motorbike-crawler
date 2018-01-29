package com.devoo.kim.service;

import com.devoo.kim.crawler.naver.AsyncNaverCafeSearchCrawler;
import com.devoo.kim.crawler.naver.NaverCafeSearchCrawler;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.query.NaverQueryCreator;
import com.devoo.kim.repository.naver.NaverItemRepository;
import com.devoo.kim.service.exception.CrawlingFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class NaverCrawlingService {

    public static final Integer MAX_PAGE_LIMIT = 0;
    private NaverCafeSearchCrawler naverCafeSearchCrawler;
    private AsyncNaverCafeSearchCrawler asyncNaverCafeSearchCrawler;
    private NaverItemRepository itemRepository;
    private NaverQueryCreator queryCreator;

    @Autowired
    public NaverCrawlingService(NaverCafeSearchCrawler naverCafeSearchCrawler,
                                AsyncNaverCafeSearchCrawler asyncNaverCafeSearchCrawler,
                                NaverItemRepository itemRepository,
                                NaverQueryCreator queryCreator) {
        this.naverCafeSearchCrawler = naverCafeSearchCrawler;
        this.asyncNaverCafeSearchCrawler = asyncNaverCafeSearchCrawler;
        this.itemRepository = itemRepository;
        this.queryCreator = queryCreator;
    }

    public void updateSaleItems(int pageLimit) throws CrawlingFailureException, IOException {
        log.info("Crawling naver sale items...");
        List<NaverItem> searchedItems = null;
        List<String> queries = queryCreator.getQueries();
        try {
            for (String query : queries) {
                searchedItems = naverCafeSearchCrawler.search(query, pageLimit, 0);
                log.info("Saving searched {} items...", searchedItems.size());
                itemRepository.saveAll(searchedItems);
                log.info("Naver sale item update completed.");
            }
        } catch (Exception e) {
            log.error("Error updating items, {}", e);
            throw new CrawlingFailureException();
        }
    }
}