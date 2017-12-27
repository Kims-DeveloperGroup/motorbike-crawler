package com.devoo.kim.service;

import com.devoo.kim.api.exception.NaverApiRequestException;
import com.devoo.kim.api.naver.NaverCafeOpenAPI;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.repository.naver.NaverItemRepository;
import com.devoo.kim.service.exception.CrawlingFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NaverCrawlingService {

    public static final Integer MAX_PAGE_LIMIT = 0;
    private final String SALE_ITEM_QUERY = "naver";
    private final String BATUMANIA_CAFE_URL = "http://cafe.naver.com/bikecargogo";
    private NaverCafeOpenAPI naverCafeOpenAPI;
    private NaverItemRepository itemRepository;

    @Autowired
    public NaverCrawlingService(NaverCafeOpenAPI naverCafeOpenAPI, NaverItemRepository itemRepository) {
        this.naverCafeOpenAPI = naverCafeOpenAPI;
        this.itemRepository = itemRepository;
    }

    public void updateSaleItems(int pageLimit) throws CrawlingFailureException {
        log.info("Crawling naver sale items...");
        List<NaverItem> searchedItems = null;
        try {
            searchedItems = naverCafeOpenAPI.search(SALE_ITEM_QUERY, pageLimit, 0);
        } catch (NaverApiRequestException e) {
            throw new CrawlingFailureException();
        }
        log.info("Saving searched  {} items...", searchedItems.size());
        itemRepository.save(searchedItems);
        log.info("Naver sale item update completed.");
    }

    public List<NaverItem> updateBatumaniaItems(int pageLimit) throws CrawlingFailureException {
        try {
            List<NaverItem> naverItems = naverCafeOpenAPI.search("바튜매 125cc 초과 팝니다", pageLimit, 0);
            return pickBatumaniaItems(naverItems);
        } catch (NaverApiRequestException e) {
            throw new CrawlingFailureException();
        }
    }

    private List<NaverItem> pickBatumaniaItems(List<NaverItem> naverItems) {
        return naverItems.stream().filter(naverItem -> {
            if (naverItem.getCafeUrl().equals(BATUMANIA_CAFE_URL)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }
}