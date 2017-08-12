package com.devoo.kim.domain.paxxo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 8. 13..
 */
@XmlRootElement
public class PaxxoPage {
    @XmlElement(name = "next")
    private int currentPage;

    @XmlElement(name = "last")
    private int lastPage;
}
