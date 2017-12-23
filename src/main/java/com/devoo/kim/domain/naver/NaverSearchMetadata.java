package com.devoo.kim.domain.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class NaverSearchMetadata {
    @JsonProperty("total")
    private long totalItems;

    private final static int MAX_PAGE_LIMIT = 11;

    /**
     * Gets the last page number of search result. (size of page equals to item counts.)
     * Also last page number should be equal or less than page limit.
     * Page number begins with zero, 0
     *
     * @param pageLimit the limit number of pages to be retrieved.
     * @return last page number of items from search result.
     */
    public long getLastPageNumber(long pageLimit) {
        log.debug("The max number of pages to crawl from naver api is {}", MAX_PAGE_LIMIT);
        return pageLimit <= MAX_PAGE_LIMIT ? pageLimit - 1 : MAX_PAGE_LIMIT - 1;
    }
}