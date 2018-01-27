package com.devoo.kim.domain.paxxo;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Document(indexName = "paxxo-model")
@XmlRootElement(name = "model")
public class PaxxoModel {
    @Id
    @XmlElement(name = "idx")
    long id;

    @XmlElement(name = "name_k")
    String name;
}
