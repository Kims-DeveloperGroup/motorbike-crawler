package com.devoo.kim.config;

import crawlercommons.fetcher.http.BaseHttpFetcher;
import crawlercommons.fetcher.http.SimpleHttpFetcher;
import crawlercommons.fetcher.http.UserAgent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpFetcherConfig {

    private int maxThreads;

    @Bean
    public BaseHttpFetcher httpFetcher() {
        UserAgent userAgent = new UserAgent("httpFetcher", null, null);
        maxThreads = 4;
        SimpleHttpFetcher httpFetcher = new SimpleHttpFetcher(maxThreads,userAgent);
        return httpFetcher;
    }
}