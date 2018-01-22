package com.devoo.kim.domain.paxxo;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;

@Getter
@XmlRootElement(name = "data", namespace = "")
public class PaxxoModels {
    @XmlElement(name = "model")
    private PaxxoModel[] models;

    public List<PaxxoModel> getModels() {
        return Arrays.asList(this.models);
    }
}
