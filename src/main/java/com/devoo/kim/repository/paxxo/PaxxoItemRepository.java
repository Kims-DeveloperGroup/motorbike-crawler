package com.devoo.kim.repository.paxxo;

import com.devoo.kim.domain.paxxo.PaxxoItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaxxoItemRepository extends ElasticsearchRepository<PaxxoItem, Long> {

    List<PaxxoItem> findByModel(String model);
}