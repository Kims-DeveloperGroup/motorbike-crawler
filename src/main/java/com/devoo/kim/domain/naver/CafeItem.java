package com.devoo.kim.domain.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
