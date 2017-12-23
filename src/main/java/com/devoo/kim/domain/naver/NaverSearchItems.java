package com.devoo.kim.domain.naver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonFormat
public class NaverSearchItems extends NaverSearchMetadata{
    private NaverItem[] naverItems;

    public NaverSearchItems(@JsonProperty("total") long totalItems,
                            @JsonProperty("items") NaverItem[] naverItems) {
        super(totalItems);
        this.naverItems = naverItems;
    }
}
