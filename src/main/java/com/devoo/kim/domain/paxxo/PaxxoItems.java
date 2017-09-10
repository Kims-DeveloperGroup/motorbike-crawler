package com.devoo.kim.domain.paxxo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;

/**
 * Contains data of each items in a single page
 */
@XmlRootElement(name = "passo", namespace = "")
public class PaxxoItems {

    @XmlElementWrapper(name = "goods")
    @XmlElement(name = "item")
    private PaxxoItem[] items;

    public List<PaxxoItem> getItems() {
        return Arrays.asList(this.items);
    }
}
