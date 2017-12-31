package com.devoo.kim.service;

import com.devoo.kim.crawler.passo.PaxxoSaleItemCrawler;
import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.repository.PaxxoRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PaxxoCrawlingService {
    private PaxxoSaleItemCrawler paxxoSaleItemCrawler;
    private PaxxoRepository paxxoRepository;
    private MessageFormat itemUrlLinkFormatter;

    @Value("${external.paxxo.item-url-pattern}")
    private String itemUrlPattern = "";

    public static final int MAX_PAGE_LIMIT = 0;

    public PaxxoCrawlingService(@Autowired PaxxoSaleItemCrawler paxxoSaleItemCrawler,
                                @Autowired PaxxoRepository indicesRepository) {
        this.paxxoSaleItemCrawler = paxxoSaleItemCrawler;
        this.paxxoRepository = indicesRepository;
    }

    /**
     * Crawls maker indices of paxxo
     */
    public void updatePaxxoMakerIndices() {
        log.info("Updating paxxo maker indices...");
        List<Maker> makerIndices = paxxoSaleItemCrawler.getMakerIndices().getMakers();
        paxxoRepository.saveMakerIndices(makerIndices);
        log.info("Paxxo maker indices are updated");
    }

    /**
     * Crawls items as much as the limited number of pages, and updates in the repository
     * @return the number of items newly updated items with its link.
     */
    public int updateItems(int pageLimit) {
        List<PaxxoItem> items;
        try {
            log.debug("Page Limit: {}", pageLimit);
            items = paxxoSaleItemCrawler.getItems(0, pageLimit);
            log.info("{} items were collected form Paxxo", items.size());
            this.itemUrlLinkFormatter = new MessageFormat(itemUrlPattern);
            for (PaxxoItem item : items) {
                item.generateUrl(itemUrlLinkFormatter);
            }
            paxxoRepository.saveItems(items);
        } catch (JAXBException e) {
            return 0;
        }
        return items.size();
    }
}
