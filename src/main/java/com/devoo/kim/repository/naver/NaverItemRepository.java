package com.devoo.kim.repository.naver;

import com.devoo.kim.domain.naver.NaverItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaverItemRepository extends ElasticsearchRepository<NaverItem, String> {
}
