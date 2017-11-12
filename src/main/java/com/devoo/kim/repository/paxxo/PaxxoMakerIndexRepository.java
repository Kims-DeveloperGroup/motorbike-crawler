package com.devoo.kim.repository.paxxo;

import com.devoo.kim.domain.paxxo.Maker;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;

public interface PaxxoMakerIndexRepository extends ElasticsearchRepository<Collection<Maker>, Long> {
}
