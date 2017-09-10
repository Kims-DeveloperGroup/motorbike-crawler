package com.devoo.kim.domain.naver;

import lombok.Data;

@Data
public class CafeItem {
    private SearchMetadata searchMetadata;
    private CafeItem[] cafeItems;
}
