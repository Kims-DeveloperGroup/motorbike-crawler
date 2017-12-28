package com.devoo.kim.parser;

import com.devoo.kim.api.naver.NaverCafeSearchCrawler;
import com.devoo.kim.domain.naver.NaverItem;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class NaverSearchResultDocumentParser {

    public List<NaverItem> parse(List<Document> documents) {
        List<NaverItem> parsedItems = new LinkedList<>();
        for (Document document : documents) {
            parsedItems.addAll(parse(document));
        }
        return parsedItems;
    }

    public List<NaverItem> parse(Document document) {
        Elements rawResultElements = extractResultItems(document);
        List<NaverItem> parsedItems = new LinkedList<>();
        rawResultElements.forEach(element -> {
            NaverItem item = new NaverItem();
            String title = extractTitle(element);
            String link = element.getElementsByClass("url").get(0).text();
            String cafeName = element.getElementsByClass("cafe_name").get(0).text();
            String description = extractDescription(element);
            item.setTitle(title);
            item.setLink(link);
            item.setCafeName(cafeName);
            item.setDescription(description);
            parsedItems.add(item);
        });
        return parsedItems;
    }

    private String extractTitle(Element element) {
        String title;
        Elements dt = element.getElementsByTag("dt");
        title = dt.get(0).child(0).text();
        return title;
    }

    private String extractDescription(Element element) {
        Element dlTag = element.getElementsByTag("dl").get(0);
        Elements ddTags = dlTag.getElementsByTag("dd");
        Element descriptionElement = ddTags.get(1);
        return descriptionElement.text();
    }

    private Elements extractResultItems(Document resultPageDocument) {
        return resultPageDocument.getElementById(NaverCafeSearchCrawler.ID_RESULT_ELEMENT)
                .getElementsByTag("li");
    }
}