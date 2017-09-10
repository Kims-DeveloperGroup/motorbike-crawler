package com.devoo.kim.api.passo;

import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.domain.paxxo.PaxxoItemMetadata;
import com.devoo.kim.domain.paxxo.PaxxoMakerIndices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    private static final String SPECIFIC_MAKER_AND_MODEL_QUERY = "@(maker_idx:@(model_idx:^maker_idx={0}#{1}#";
    private static final String ALL_ITEM_QUERY = "@(maker_idx:";

    /**
     * Get Items from Paxxo. Items are not guaranteed to be in order.
     * @param limitOfPage : the number of page to be retrieved.
     * @return items
     * @throws JAXBException in case of failure to parse items in xml.
     */
    public List<PaxxoItem> getItems(int limitOfPage) throws JAXBException {
        PaxxoItemMetadata result = queryItemInformation("","", 0);
        int lastPage =  result.getLastPage() > limitOfPage ? limitOfPage : result.getLastPage();
        List<PaxxoItem> items = new ArrayList<>(result.getCount());
        items.addAll(result.getItems());
        for (int current =1; current <= lastPage; current++) {
            result = queryItemInformation("", "", current);
            items.addAll(result.getItems());
        }
        return items;
    }

    /**
     * Query items metadata, which contains count of items and pagination info.
     * @param maker as a search input
     * @param model as a search input
     * @param page  page number of the items to search.
     * @return PaxxoItemMetadata that contains metadata of the item to search.
     * @throws JAXBException
     */
    public PaxxoItemMetadata queryItemInformation(String maker, String model, int page) throws JAXBException {
        MultiValueMap<String, String> searchForm = makeSearchForm(maker, model, page);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(searchForm, headers());
        return restTemplate.postForObject(itemSearchApi, requestEntity, PaxxoItemMetadata.class);
    }

    /**
     * Get information of maker indices in paxxo.
     * @return
     */
    public PaxxoMakerIndices getMakerIndices() {
        return restTemplate.getForObject(makerCountryIndexApi, PaxxoMakerIndices.class);
    }

    private MultiValueMap<String, String> makeSearchForm(String maker, String model, int page) {
        MultiValueMap<String, String> searchForm = form();
        MessageFormat messageFormat;
        if (maker.equals("") && model.equals("")){
            messageFormat = new MessageFormat(ALL_ITEM_QUERY);
        }else {
            messageFormat = new MessageFormat(SPECIFIC_MAKER_AND_MODEL_QUERY);
        }
        String searchValue = messageFormat.format(new String[]{maker, model});
        searchForm.add("query", searchValue);
        searchForm.add("page", String.valueOf(page));
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
