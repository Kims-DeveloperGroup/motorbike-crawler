package com.devoo.kim.domain.naver;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class NaverSearchMetadata {

    @JsonProperty("total")
    private long totalItems;
}
