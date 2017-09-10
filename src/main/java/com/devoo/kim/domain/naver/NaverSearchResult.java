package com.devoo.kim.domain.naver;

import lombok.Getter;

@Getter
public class NaverSearchResult {
    private SearchMetadata searchMetadata;
    private CafeItem[] cafeItems;
}
