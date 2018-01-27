package com.devoo.kim.repository;

import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.domain.paxxo.PaxxoModel;
import com.devoo.kim.repository.paxxo.PaxxoItemRepository;
import com.devoo.kim.repository.paxxo.PaxxoMakerIndexRepository;
import com.devoo.kim.repository.paxxo.PaxxoModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by rikim on 2017. 8. 22..
 */
@Repository
@Slf4j
public class PaxxoRepository {
    private final PaxxoItemRepository itemRepository;
    private final PaxxoMakerIndexRepository makerIndexRepository;
    private PaxxoModelRepository paxxoModelRepository;

    @Autowired
    public PaxxoRepository(PaxxoItemRepository paxxoItemRepository,
                           PaxxoMakerIndexRepository makerIndexRepository,
                           PaxxoModelRepository paxxoModelRepository) {
        this.itemRepository = paxxoItemRepository;
        this.makerIndexRepository = makerIndexRepository;
        this.paxxoModelRepository = paxxoModelRepository;
    }

    public void saveMakerIndices(Collection<Maker> makers) {
        makerIndexRepository.save(makers);
    }

    public void saveItems(Collection<PaxxoItem> items) {
        itemRepository.save(items);
        log.info("{} items are saved in repository", items.size());
    }

    public void saveModels(List<PaxxoModel> models) {
        paxxoModelRepository.save(models);
        log.info("{} models are saved in repository", models.size());
    }
}