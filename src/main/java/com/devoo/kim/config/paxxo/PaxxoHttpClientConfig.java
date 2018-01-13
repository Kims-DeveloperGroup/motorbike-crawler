package com.devoo.kim.config.paxxo;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class PaxxoHttpClientConfig {

    @Value("${external.paxxo.httpclient.connectionTimeout}")
    private int connectionTimeoutInMills;

    @Value("${external.paxxo.httpclient.readTimeout}")
    private int readTimeoutInMills;

    @Value("${external.paxxo.httpclient.connectionTimeToLive}")
    private int connectionTimeToLive;

    @Value("${external.paxxo.httpclient.maxTotalConnection}")
    private int maxTotalConnection;

    @Value("${external.paxxo.httpclient.maxConnectionPerRoute}")
    private int maxConnectionPerRoute;

    @Bean
    public HttpComponentsClientHttpRequestFactory paxxoHttpClientRequestFactory() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(maxTotalConnection)
                .setMaxConnPerRoute(maxConnectionPerRoute)
                .setConnectionTimeToLive(connectionTimeToLive, TimeUnit.MILLISECONDS)
                .build();
        HttpComponentsClientHttpRequestFactory httpClientRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpClientRequestFactory.setHttpClient(httpClient);
        httpClientRequestFactory.setConnectTimeout(connectionTimeoutInMills);
        httpClientRequestFactory.setReadTimeout(readTimeoutInMills);
        return httpClientRequestFactory;
    }
}
