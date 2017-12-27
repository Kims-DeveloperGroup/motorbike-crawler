package com.devoo.kim.api.naver;

import com.devoo.kim.api.exception.NaverApiRequestException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class NaverCafeSearchCrawler {
    private String cafeSearchRootUrl;
    private String fragmentPattern = "\"query\":\"{0}\",\"sortBy\":0,\"period\":[],\"menuType\":[0],\"searchBy\":0,\"duplicate\":false,\"inCafe\":\"\",\"withOutCafe\":\"\",\"includeAll\":\"\",\"exclude\":\"\",\"include\":\"\",\"exact\":\"\",\"page\":{1},\"escrow\":\"\",\"onSale\":\"\"";

    private static MessageFormat urlFragmentFormatter;
    private RestTemplate restTemplate;

    public static final int PAGE_SIZE = 10;
    public static final String ID_RESULT_ELEMENT = "ArticleSearchResultArea";

    @Autowired
    public NaverCafeSearchCrawler(
            @Value("${external.naver.cafeSearch.rootUrl}") String cafeSearchRootUrl
    ) {
        this.urlFragmentFormatter = new MessageFormat(fragmentPattern);
        this.restTemplate = new RestTemplate();
        this.cafeSearchRootUrl = cafeSearchRootUrl;

    }

    public List<Elements> search(String query, int pageLimit, int startPageNumber) throws NaverApiRequestException {

        List<Elements> resultElements = new LinkedList<>();
        for (int currentPageNumber = startPageNumber; currentPageNumber < pageLimit; currentPageNumber++) {
            Document document = getDocuments(query, currentPageNumber);
            Elements elementsInPage = extractResultItems(document);
            resultElements.add(elementsInPage);
        }
        return resultElements;
    }

    Document getDocuments(String query, Integer pageNumber) {
        String fragment = urlFragmentFormatter.format(new String[]{query, pageNumber.toString()});
        String url = UriComponentsBuilder.fromHttpUrl(cafeSearchRootUrl)
                .queryParam("query", query).fragment("{" + fragment + "}").toUriString();
        log.debug("Request to {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return Jsoup.parse(response.getBody());
    }

    private Elements extractResultItems(Document resultPageDocument) {
        return resultPageDocument.getElementById(NaverCafeSearchCrawler.ID_RESULT_ELEMENT)
                .getElementsByTag("li");
    }
}