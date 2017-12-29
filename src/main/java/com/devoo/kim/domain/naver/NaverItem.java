package com.devoo.kim.domain.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "naver-item")
public class NaverItem {
    @Id
    private String link;
    private String title;
    private String description;

    @JsonProperty("cafename")
    private String cafeName;

    @JsonProperty("cafeurl")
    private String cafeUrl;
    private String query;
}
