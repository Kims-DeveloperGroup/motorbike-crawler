package com.devoo.kim.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import crawlercommons.fetcher.FetchedResult;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by rikim on 2017. 7. 16..
 */
@JsonFormat
public class FetchedResultWrapper {
    @JsonProperty
    private final String hostUrl;
    @JsonProperty
    private final long fetchedTime;
    @JsonProperty
    private byte[] content;

    public FetchedResultWrapper(FetchedResult fetchedResult) {
        this.fetchedTime = fetchedResult.getFetchTime();
        this.hostUrl = fetchedResult.getHostAddress();
        this.content = fetchedResult.getContent();
    }
}
