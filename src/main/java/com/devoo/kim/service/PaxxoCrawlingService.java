package com.devoo.kim.service;

import com.devoo.kim.api.passo.PaxxoApiClient;
import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.repository.PaxxoIndicesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by rikim on 2017. 8. 14..
 */
public class PaxxoCrawlingService {
    private PaxxoApiClient paxxoApiClient;
    private PaxxoIndicesRepository paxxoIndicesRepository;
    public PaxxoCrawlingService(@Autowired PaxxoApiClient paxxoApiClient,
                                @Autowired PaxxoIndicesRepository indicesRepository) {
        this.paxxoApiClient = paxxoApiClient;
        this.paxxoIndicesRepository = indicesRepository;
    }

    public void updatePaxxoMakerIndices() {
        List<Maker> makerIndices = paxxoApiClient.getMakerIndices().getMakers();
        paxxoIndicesRepository.save(makerIndices);
    }
}
