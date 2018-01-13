package com.devoo.kim.domain.paxxo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 7. 30..
 */
@Slf4j
@XmlRootElement(name = "passo", namespace = "")
public class PaxxoItemMetadata {
    @Getter
    @XmlElement
    private int count;

    @XmlElement(name = "page")
    private PaxxoPagination page;

    /**
     * Gets the last page number of items from paxxo. (size of page equals to item counts.)
     * Also last page number should be equal or less than page limit.
     * Page number begins with zero, 0
     *
     * @param pageLimit the limit number of pages to be retrieved.
     * @return last page number of items
     * (normally startPageNumber plus pageLimit in case that actual last page number is bigger than that).
     */
    public int getLastPageNumber(int startPageNumber, int pageLimit) {
        log.debug("Maximum page count from Paxxo: {}", page.getLastPage());
        if (page.getLastPage() < startPageNumber + pageLimit - 1 || pageLimit == 0) {
            return page.getLastPage();
        }
        return startPageNumber + pageLimit - 1;
    }
}
