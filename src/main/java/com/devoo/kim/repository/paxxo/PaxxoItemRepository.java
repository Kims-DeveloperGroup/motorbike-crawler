package com.devoo.kim.repository.paxxo;

import com.devoo.kim.domain.paxxo.PaxxoItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PaxxoItemRepository extends ElasticsearchRepository<PaxxoItem, Long> {

    List<PaxxoItem> findByModel(String model);
}