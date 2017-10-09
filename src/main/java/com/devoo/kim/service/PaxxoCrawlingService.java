package com.devoo.kim.service;

import com.devoo.kim.api.passo.PaxxoApiClient;
import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.repository.PaxxoIndicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by rikim on 2017. 8. 14..
 * Crawls items in paxxo.
 */
@Service
public class PaxxoCrawlingService {
    private PaxxoApiClient paxxoApiClient;
    private PaxxoIndicesRepository paxxoIndicesRepository;
    private MessageFormat itemUrlLinkFormatter;

    @Value("${external.paxxo.item-url-pattern}")
    private String itemUrlPattern = "";

    private int pageLimit = 3;
    public PaxxoCrawlingService(@Autowired PaxxoApiClient paxxoApiClient,
                                @Autowired PaxxoIndicesRepository indicesRepository) {
        this.paxxoApiClient = paxxoApiClient;
        this.paxxoIndicesRepository = indicesRepository;
    }

    /**
     * Crawls maker indices of paxxo
     */
    public void updatePaxxoMakerIndices() {
        List<Maker> makerIndices = paxxoApiClient.getMakerIndices().getMakers();
        paxxoIndicesRepository.save(makerIndices);
    }

    /**
     * Crawls items as much as the limited number of pages, and updates in the repository
     * @return the number of items newly updated items with its link.
     */
    public int updateItems() {
        List<PaxxoItem> items;
        try {
            items = paxxoApiClient.getItems(pageLimit);
            this.itemUrlLinkFormatter = new MessageFormat(itemUrlPattern);
            for (PaxxoItem item : items) {
                item.generateUrl(itemUrlLinkFormatter);
            }
            paxxoIndicesRepository.saveItems(items);
        } catch (JAXBException e) {
            return 0;
        }
        return items.size();
    }
}
