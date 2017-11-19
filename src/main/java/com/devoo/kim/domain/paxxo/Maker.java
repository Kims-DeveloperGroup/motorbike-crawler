package com.devoo.kim.domain.paxxo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rikim on 2017. 8. 14..
 */
@Data
@Document(indexName = "maker")
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
public class Maker {
    @Id
    @XmlElement(name = "maker_idx")
    private long id;

    @XmlElement(name = "maker_name")
    private String name;
}