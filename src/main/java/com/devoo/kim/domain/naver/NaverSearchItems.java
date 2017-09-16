package com.devoo.kim.domain.naver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@JsonFormat
public class NaverSearchItems extends NaverSearchMetadata{
    private CafeItem[] cafeItems;

    public NaverSearchItems(@JsonProperty("total") long totalItems,
                            @JsonProperty("items") CafeItem[] cafeItems) {
        super(totalItems);
        this.cafeItems = cafeItems;
    }
}
