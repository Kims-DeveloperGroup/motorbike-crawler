package com.devoo.kim.domain.paxxo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 8. 10..
 */
@XmlRootElement(name = "data")
public class PaxxoMakerIndices {
    @XmlElement(name = "country")
    private PaxxoMakerIndexByCountry[] makerIndexByCountries;
}
