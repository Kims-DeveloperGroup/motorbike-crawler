package com.devoo.kim.domain.paxxo;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 8. 14..
 */
@XmlRootElement(name = "country")
@Getter
public class PaxxoMakerIndexByCountry {
    @XmlElement(name = "maker")
    private Maker[] makers;
}
