package com.devoo.kim.data.paxxo;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 7. 30..
 */
@XmlRootElement(name = "passo", namespace = "")
public class PaxxoDataSet {

    @XmlElement
    private String count;
}
