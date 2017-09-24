package com.devoo.kim.domain.paxxo;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 7. 30..
 */
@XmlRootElement(name = "passo", namespace = "")
public class PaxxoItemMetadata {
    @Getter
    @XmlElement
    private int count;

    @XmlElement(name = "page")
    private PaxxoPage page;

<<<<<<< HEAD
    /**
     * Gets the last page number of search result. (size of page equals to item counts.)
     * Also last page number should be equal or less than page limit.
     * Page number begins with zero, 0
     *
     * @param pageLimit the limit number of pages to be retrieved.
     * @return last page number of items from search result.
     */
    public int getLastPageNumber(int pageLimit) {
        int lastPageNumber = page.getLastPage();
        if (page.getLastPage() < pageLimit - 1 || pageLimit == 0) {
            return page.getLastPage();
        }
        return pageLimit - 1;
=======
    public int getLastPage() {
        return page.getLastPage();
>>>>>>> url-generation
    }
}
