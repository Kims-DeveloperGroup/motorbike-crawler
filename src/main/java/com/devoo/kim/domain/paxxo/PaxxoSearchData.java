package com.devoo.kim.domain.paxxo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 7. 30..
 */
@XmlRootElement(name = "passo", namespace = "")
public class PaxxoSearchData {

    @XmlElement
    private String count;

    @XmlElementWrapper(name = "goods")
    @XmlElement(name = "item")
    private PaxxoItem[] items;

    @XmlElement(name = "page")
    private PaxxoPage page;
}
