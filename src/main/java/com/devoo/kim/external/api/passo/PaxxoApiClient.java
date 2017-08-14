package com.devoo.kim.external.api.passo;

import com.devoo.kim.domain.paxxo.PaxxoMakerIndices;
import com.devoo.kim.domain.paxxo.PaxxoSearchResult;
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rikim on 2017. 7. 30..
 */
@Service
public class PaxxoApiClient {

    @Value("${external.paxxo.item-search-api}")
    private String itemSearchApi;

    @Value("${external.paxxo.country-maker-index-api}")
    private String makerCountryIndexApi;

    private RestTemplate restTemplate = new RestTemplate();

    public PaxxoSearchResult search(String maker, String model) throws JAXBException {
        MultiValueMap<String, String> searchForm = makeSearchForm(maker, model);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(searchForm, headers());
        Jaxb2RootElementHttpMessageConverter jaxbMessageConverter = new Jaxb2RootElementHttpMessageConverter();
        return restTemplate.postForObject(itemSearchApi, requestEntity, PaxxoSearchResult.class);
    }

    public PaxxoMakerIndices getCountryAndMakerIndex() {
        return restTemplate.getForObject(makerCountryIndexApi, PaxxoMakerIndices.class);
    }

    private MultiValueMap<String, String> makeSearchForm(String maker, String model) {
        MultiValueMap<String, String> searchForm = form();
        MessageFormat messageFormat = new MessageFormat("@(maker_idx:@(model_idx:^maker_idx={0}#{1}#");
        String searchValue = messageFormat.format(new String[]{maker, model});
        searchForm.add("search", searchValue);
        return searchForm;
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
}
