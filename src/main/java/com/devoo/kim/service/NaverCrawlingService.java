package com.devoo.kim.service;

import com.devoo.kim.api.exception.NaverApiRequestException;
import com.devoo.kim.api.naver.NaverCafeAPI;
import com.devoo.kim.domain.naver.NaverItem;
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
    private final String SALE_ITEM_QUERY = "naver";
    private NaverCafeAPI naverCafeAPI;
    private NaverItemRepository itemRepository;

    @Autowired
    public NaverCrawlingService(NaverCafeAPI naverCafeAPI, NaverItemRepository itemRepository) {
        this.naverCafeAPI = naverCafeAPI;
        this.itemRepository = itemRepository;
    }

    public void updateSaleItems(int pageLimit) throws CrawlingFailureException {
        log.info("Crawling naver sale items...");
        List<NaverItem> searchedItems = null;
        try {
            searchedItems = naverCafeAPI.search(SALE_ITEM_QUERY, pageLimit, 0);
        } catch (NaverApiRequestException e) {
            throw new CrawlingFailureException();
        }
        log.info("Saving searched  {} items...", searchedItems.size());
        itemRepository.save(searchedItems);
        log.info("Naver sale item update completed.");
    }
}