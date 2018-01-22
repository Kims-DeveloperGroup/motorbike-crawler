package com.devoo.kim.repository.paxxo;

import com.devoo.kim.domain.paxxo.PaxxoModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaxxoModelRepository extends ElasticsearchRepository<PaxxoModel, Long> {
}