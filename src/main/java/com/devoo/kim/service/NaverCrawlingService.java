package com.devoo.kim.service;

import com.devoo.kim.crawler.naver.AsyncNaverCafeSearchCrawler;
import com.devoo.kim.domain.naver.NaverItem;
import com.devoo.kim.parser.NaverSearchResultDocumentParser;
import com.devoo.kim.query.NaverQueryCreator;
import com.devoo.kim.repository.naver.NaverItemRepository;
import com.devoo.kim.service.exception.CrawlingFailureException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class NaverCrawlingService {

    public static final Integer MAX_PAGE_LIMIT = 0;
    private AsyncNaverCafeSearchCrawler asyncNaverCafeSearchCrawler;
    private NaverItemRepository itemRepository;
    private NaverQueryCreator queryCreator;
    private NaverSearchResultDocumentParser naverSearchResultDocumentParser;

    @Autowired
    public NaverCrawlingService(AsyncNaverCafeSearchCrawler asyncNaverCafeSearchCrawler,
                                NaverItemRepository itemRepository,
                                NaverQueryCreator queryCreator,
                                NaverSearchResultDocumentParser naverSearchResultDocumentParser) {
        this.asyncNaverCafeSearchCrawler = asyncNaverCafeSearchCrawler;
        this.itemRepository = itemRepository;
        this.queryCreator = queryCreator;
        this.naverSearchResultDocumentParser = naverSearchResultDocumentParser;
    }

    public void updateSaleItems(int pageLimit) throws CrawlingFailureException, IOException {
        log.info("Crawling naver sale items...");
        List<String> queries = queryCreator.getQueries();
        for (String query : queries) {
            asyncNaverCafeSearchCrawler.getDocuments(query, pageLimit)
                    .subscribe(mono -> mono.subscribe(document -> {
                        List<NaverItem> items =
                                naverSearchResultDocumentParser.parse(Jsoup.parse(document), query);
                        itemRepository.saveAll(items);
                        log.info("Saved {} items from {}", items.size(), query);
                    }));
        }
    }
}