package com.devoo.kim.repository;

import com.devoo.kim.domain.paxxo.PaxxoItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public class PaxxoItemRepository implements ElasticsearchRepository<PaxxoItem, Long> {

    List<PaxxoItem> findByModel(String model);
}