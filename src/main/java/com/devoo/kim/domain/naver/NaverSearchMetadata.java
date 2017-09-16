package com.devoo.kim.domain.naver;

import com.devoo.kim.api.naver.NaverCafeAPI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@AllArgsConstructor
public class NaverSearchMetadata {
    @JsonProperty("total")
    private long totalItems;

    public long getLastPage(long pageLimit) {
        return this.totalItems / NaverCafeAPI.pageSize;
    }
}
