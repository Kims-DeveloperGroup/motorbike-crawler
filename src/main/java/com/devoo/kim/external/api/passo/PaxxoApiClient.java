package com.devoo.kim.external.api.passo;

import com.devoo.kim.data.paxxo.PaxxoDataSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.InputType.file;

/**
 * Created by rikim on 2017. 7. 30..
 */
@Service
public class PaxxoApiClient {

    @Value("${external.paxxo.url}")
    private String requestUrl;

    RestTemplate restTemplate = new RestTemplate();

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


}
