package com.devoo.kim.domain.naver;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@AllArgsConstructor
public class NaverSearchMetadata {
    @JsonProperty("total")
    private long totalItems;
}
