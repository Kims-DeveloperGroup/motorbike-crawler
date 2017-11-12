package com.devoo.kim.domain.naver;

import com.devoo.kim.api.naver.NaverCafeAPI;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NaverSearchMetadata {
    @JsonProperty("total")
    private long totalItems;

    /**
     * Gets the last page number of search result. (size of page equals to item counts.)
     * Also last page number should be equal or less than page limit.
     * Page number begins with zero, 0
     *
     * @param pageLimit the limit number of pages to be retrieved.
     * @return last page number of items from search result.
     */
    public long getLastPageNumber(long pageLimit) {
        long numberOfPages = (this.totalItems / NaverCafeAPI.pageSize);
        numberOfPages = (this.totalItems % NaverCafeAPI.pageSize > 0) ? ++numberOfPages : numberOfPages;
        if (numberOfPages < pageLimit) {
            return numberOfPages - 1;
        }
        return pageLimit - 1;
    }
}