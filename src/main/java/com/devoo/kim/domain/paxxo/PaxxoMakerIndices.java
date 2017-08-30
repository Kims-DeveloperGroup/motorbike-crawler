package com.devoo.kim.domain.paxxo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rikim on 2017. 8. 10..
 */
@XmlRootElement(name = "data")
public class PaxxoMakerIndices {
    @XmlElement(name = "country")
    private PaxxoMakerIndexByCountry[] makerIndexByCountries;

    public List<Maker> getMakers() {
        List<Maker> makers = new ArrayList<>();
        for (PaxxoMakerIndexByCountry country: makerIndexByCountries) {
            makers.addAll(Arrays.asList(country.getMakers()));
        }
        return makers;
    }
}
