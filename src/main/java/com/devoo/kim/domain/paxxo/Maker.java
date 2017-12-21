package com.devoo.kim.domain.paxxo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 8. 14..
 */
@XmlRootElement
public class Maker {
    @XmlElement(name = "maker_idx")
    private long id;

    @XmlElement(name = "maker_name")
    private String name;
}
