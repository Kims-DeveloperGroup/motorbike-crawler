package com.devoo.kim.service;

import com.devoo.kim.crawler.passo.PaxxoSaleItemCrawler;
import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.domain.paxxo.PaxxoItem;
import com.devoo.kim.repository.PaxxoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by rikim on 2017. 8. 14..
 * Crawls items in paxxo.
 */
@Service
@Slf4j
public class PaxxoCrawlingService {
    private PaxxoSaleItemCrawler paxxoSaleItemCrawler;
    private PaxxoRepository paxxoRepository;
    private TaskExecutor taskExecutor;
    private MessageFormat itemUrlLinkFormatter;

    @Value("${external.paxxo.item-url-pattern}")
    private String itemUrlPattern = "";

    @Value("${external.paxxo.job.chunkSize}")
    private int pageChunkSize = 100;

    public static final int MAX_PAGE_LIMIT = 0;

    @Autowired
    public PaxxoCrawlingService(PaxxoSaleItemCrawler paxxoSaleItemCrawler,
                                PaxxoRepository indicesRepository,
                                @Qualifier("paxxoTaskExecutor") TaskExecutor taskExecutor) {
        this.paxxoSaleItemCrawler = paxxoSaleItemCrawler;
        this.paxxoRepository = indicesRepository;
        this.taskExecutor = taskExecutor;
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
     * Crawling starts from startPageNumber
     * @param startPageNumber the number of page to start crawling (min = 0)
     * @param pageLimit max page count of crawling items
     * @return the number of items newly updated items with its link.
     */
    public int updateItems(int startPageNumber, int pageLimit,
                           CountDownLatch countDownLatch,
                           Instant initTime, ArrayList<Long> timeLines) {
        this.itemUrlLinkFormatter = new MessageFormat(itemUrlPattern);
        List<PaxxoItem> items = new ArrayList<>();
        try {
            Instant startTime = Instant.now();
            items = paxxoSaleItemCrawler.getItems(startPageNumber, pageLimit);
            log.info("{} items were collected form Paxxo", items.size());
            for (PaxxoItem item : items) {
                item.generateUrl(itemUrlLinkFormatter);
            }
            paxxoRepository.saveItems(items);
            Instant endTime = Instant.now();
            log.info("Crawling completed from page {} - {}: time: {} seconds.",
                    startPageNumber,
                    startPageNumber + pageLimit - 1, Duration.between(startTime, endTime).toMillis() / 1000);
        } catch (Exception e) {
            log.error("Exception in page {} : {}", startPageNumber, e);
        } finally {
            countDownLatch.countDown();
            long timeline = Duration.between(initTime, Instant.now()).toMillis();
            timeLines.add(timeline);
            log.debug("{} tasks remains to be completed @ {}",
                    countDownLatch.getCount(), timeline);
        }
        return items.size();
    }

    /**
     * Crawls the items (Multi-threading)
     *
     * @return
     */
    public void updateItemsWithTaskExecutor(int startPageNumber, int pageLimit) {
        CountDownLatch countDown = new CountDownLatch(pageLimit / pageChunkSize);
        Instant startTime = Instant.now();
        ArrayList<Long> timeLines = new ArrayList<>();
        for (; startPageNumber < pageLimit; startPageNumber += this.pageChunkSize) {
            final int pageNumber = startPageNumber;
            log.debug("Task request: updating paxxo items in page {} - {}", pageNumber, pageNumber + pageChunkSize - 1);
            taskExecutor.execute(() -> {
                PaxxoCrawlingService.this.updateItems(pageNumber, pageChunkSize,
                        countDown, startTime, timeLines);
            });
        }
        try {
            countDown.await();
            Instant endTime = Instant.now();
            log.info("{} pages crawling time : {} seconds.", pageLimit, Duration.between(startTime, endTime).toMillis() / 1000);
            timeLines.forEach(timeline -> {
//                log.debug("{}", timeline.longValue());
                System.out.println(timeline.longValue());
            });
        } catch (InterruptedException e) {
            log.error("{}", e);
        }
    }
}
