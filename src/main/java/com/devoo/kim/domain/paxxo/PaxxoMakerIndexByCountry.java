package com.devoo.kim.domain.paxxo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 8. 14..
 */
@XmlRootElement(name = "country")
public class PaxxoMakerIndexByCountry {
    @XmlElement(name = "idx")
    private long countryId;

    @XmlElement(name = "name_k")
    private String countryName;

    @XmlElement(name = "maker")
    private Maker[] makers;
}
