package com.devoo.kim.repository.paxxo;

import com.devoo.kim.domain.paxxo.Maker;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PaxxoMakerIndexRepository extends ElasticsearchRepository<Collection<Maker>, Long> {
}
