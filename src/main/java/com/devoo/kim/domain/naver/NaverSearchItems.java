package com.devoo.kim.domain.naver;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat
public class NaverSearchItems extends NaverSearchMetadata {
    private CafeItem[] cafeItems;
}
