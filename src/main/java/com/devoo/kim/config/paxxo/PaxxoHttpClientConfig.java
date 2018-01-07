package com.devoo.kim.config.paxxo;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class PaxxoHttpClientConfig {

    private final int connectionTimeoutInMills = 10 * 1000;
    private final int readTimeoutInMills = connectionTimeoutInMills * 2;

    @Bean
    public HttpComponentsClientHttpRequestFactory paxxoHttpClientRequestFactory() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(16)
                .setMaxConnPerRoute(8)
                .setConnectionTimeToLive(1, TimeUnit.MINUTES)
                .build();
        HttpComponentsClientHttpRequestFactory httpClientRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpClientRequestFactory.setHttpClient(httpClient);
        httpClientRequestFactory.setConnectTimeout(connectionTimeoutInMills);
        httpClientRequestFactory.setReadTimeout(readTimeoutInMills);
        return httpClientRequestFactory;
    }
}
