package com.devoo.kim.crawler.passo;

import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.domain.paxxo.PaxxoItemMetadata;
import com.devoo.kim.domain.paxxo.PaxxoItems;
import com.devoo.kim.domain.paxxo.PaxxoMakerIndices;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PaxxoSaleItemCrawler {

    @Value("${external.paxxo.item-search-api}")
    private String itemSearchApi;

    @Value("${external.paxxo.country-maker-index-api}")
    private String makerCountryIndexApi;

    private RestTemplate restTemplate = new RestTemplate();

    private static final String SPECIFIC_MAKER_AND_MODEL_QUERY = "@(maker_idx:@(model_idx:^maker_idx={0}#{1}#";
    private static final String ALL_ITEM_QUERY = "@(maker_idx:";

    /**
     * Get Items from Paxxo. Items are not guaranteed to be in order.
     * @param startPageNumber A page number of beginning for crawling (min: 0)
     * @param pageLimit : the number of page to be retrieved.
     * @return items
     * @throws JAXBException in case of failure to parse items in xml.
     */
    public List<PaxxoItem> getItems(int startPageNumber, int pageLimit) throws JAXBException {
        PaxxoItemMetadata metadata = getItemMetadata("", "");
        int lastPage = metadata.getLastPageNumber(pageLimit);
        List<PaxxoItem> items = new ArrayList<>();
        for (int current = startPageNumber; current <= lastPage; current++) {
            items.addAll(getItemsInPage("", "", current));
        }
        return items;
    }

    /**
     * Get item's metadata, which contains count of items and pagination info.
     * No Item data is returned, but only metadata of stored data for the item.
     * @param maker as a search input
     * @param model as a search input
     * @return PaxxoItemMetadata that contains metadata of the item for a given maker and a given model .
     * @throws JAXBException
     */
    public PaxxoItemMetadata getItemMetadata(String maker, String model) throws JAXBException {
        MultiValueMap<String, String> searchForm = makeSearchForm(maker, model, 0);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(searchForm, headers());
        log.debug("Getting metadata from Paxxo.");
        return restTemplate.postForObject(itemSearchApi, requestEntity, PaxxoItemMetadata.class);
    }

    /**
     * Get Items only in a given page for a given maker and model.
     * If no maker and model is specified, unconditionally items are retrieved and  unordered.
     *
     * @param maker as a search input
     * @param model as a search input
     * @param page  page number of item
     * @return items in the given page
     */
    private List<PaxxoItem> getItemsInPage(String maker, String model, int page) {
        MultiValueMap<String, String> searchForm = makeSearchForm(maker, model, page);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(searchForm, headers());
        log.debug("collecting items in page {}", page);
        return restTemplate.postForObject(itemSearchApi, requestEntity, PaxxoItems.class).getItems();
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
