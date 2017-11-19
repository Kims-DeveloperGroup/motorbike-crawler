package com.devoo.kim.repository;

import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.repository.paxxo.PaxxoItemRepository;
import com.devoo.kim.repository.paxxo.PaxxoMakerIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by rikim on 2017. 8. 22..
 */
@Repository
public class PaxxoRepository {
    private final PaxxoItemRepository itemRepository;
    private final PaxxoMakerIndexRepository makerIndexRepository;

    @Autowired
    public PaxxoRepository(PaxxoItemRepository paxxoItemRepository,
                           PaxxoMakerIndexRepository makerIndexRepository) {
        this.itemRepository = paxxoItemRepository;
        this.makerIndexRepository = makerIndexRepository;
    }

    public void saveMakerIndices(Collection<Maker> makers) {
        makerIndexRepository.saveAll(makers);
    }

    public void saveItems(Collection<PaxxoItem> items) {
        itemRepository.saveAll(items);
    }
}
