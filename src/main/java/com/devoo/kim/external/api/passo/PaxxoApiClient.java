package com.devoo.kim.external.api.passo;

import com.devoo.kim.domain.paxxo.PaxxoDataIndex;
import com.devoo.kim.domain.paxxo.PaxxoDataSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rikim on 2017. 7. 30..
 */
@Service
public class PaxxoApiClient {

    @Value("${external.paxxo.item-search-api}")
    private String requestUrl;

    @Value("${external.paxxo.country-maker-index-api}")
    private String makerCountryIndexApi;

    private RestTemplate restTemplate = new RestTemplate();

    public PaxxoDataSet search(String maker, String model) throws JAXBException {
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(form(), headers());
        Jaxb2RootElementHttpMessageConverter jaxbMessageConverter = new Jaxb2RootElementHttpMessageConverter();
        List<MediaType> mediaTypeList = new ArrayList<>();
        return restTemplate.postForObject(requestUrl, requestEntity, PaxxoDataSet.class);
    }

    private MultiValueMap<String, Object> headers() {
        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return headers;
    }

    private MultiValueMap<String, String> form() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("part", "cybershop");
        form.add("path", "cybershop");
        form.add("mode", "process");
        form.add("process", "list");
        return form;
    }

    public PaxxoDataIndex getCountryAndMakerIndex() {
        return restTemplate.getForObject(makerCountryIndexApi, PaxxoDataIndex.class);
    }
}
