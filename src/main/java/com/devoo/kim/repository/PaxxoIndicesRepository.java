package com.devoo.kim.repository;

import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.domain.paxxo.PaxxoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by rikim on 2017. 8. 22..
 */
@Repository
public class PaxxoIndicesRepository {
    private final PaxxoItemRepository itemRepository;

    @Autowired
    public PaxxoIndicesRepository(PaxxoItemRepository paxxoItemRepository) {
        this.itemRepository = paxxoItemRepository;
    }

    public void save(Collection<Maker> makers) {}

    public void saveItems(Collection<PaxxoItem> items) {
        itemRepository.saveAll(items);
    }
}
