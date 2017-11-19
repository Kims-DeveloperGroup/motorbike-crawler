package com.devoo.kim.domain.paxxo;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.MessageFormat;

/**
 * Created by rikim on 2017. 8. 12..
 */
@Document(indexName = "paxxo-item")
@XmlRootElement(name = "item")
public class PaxxoItem {
    @Id
    @XmlElement(name = "idx")
    long id;

    @XmlElement
    String title;

    @XmlElement
    String maker;

    @XmlElement
    String model;

    @XmlElement(name = "out_year")
    int releaseYear;

    @XmlElement(name = "sale_price")
    String price ;

    @XmlElement(name = "area")
    String region;

    @XmlElement(name = "update_date")
    String updateDate;

    @XmlElement(name = "state")
    int salesStatus;

    @Getter
    private String url;

    public void generateUrl(MessageFormat itemUrlFormatter) {
        this.url = itemUrlFormatter.format(new String[]{String.valueOf(this.id)});
    }
}
