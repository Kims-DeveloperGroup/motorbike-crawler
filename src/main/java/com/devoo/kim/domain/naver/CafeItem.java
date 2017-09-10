package com.devoo.kim.domain.naver;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class CafeItem {
    private String title;
    private String link;
    private String description;

    @JsonProperty("cafename")
    private String cafeName;

    @JsonProperty("cafeurl")
    private String cafeUrl;

}
