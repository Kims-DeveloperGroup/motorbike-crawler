package com.devoo.kim.service;

import com.devoo.kim.config.paxxo.PaxxoHttpClientConfig;
import com.devoo.kim.crawler.passo.PaxxoSaleItemCrawler;
import com.devoo.kim.repository.PaxxoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PaxxoSaleItemCrawler.class, PaxxoHttpClientConfig.class, ThreadPoolTaskExecutor.class})
public class PaxxoCrawlingServicePerformanceTest {

    private final int PAGE_LIMIT_800 = 800;
    private final int PAGE_LIMIT_1600 = 1600;
    @Autowired
    private PaxxoSaleItemCrawler paxxoSaleItemCrawler;

    private PaxxoRepository paxxoRepository = Mockito.mock(PaxxoRepository.class);

    @Test
    public void computeTotalRunningTime_whenMaxThreadCountIs4AndPageLimit800() {
        //Given
        mockPaxxoRepositoryMethods();
        int givenPoolSizeToBeTested = 4;
        TaskExecutor taskExecutor = initializeTaskExecutor(givenPoolSizeToBeTested);
        PaxxoCrawlingService paxxoCrawlingService = new PaxxoCrawlingService(paxxoSaleItemCrawler, paxxoRepository, taskExecutor);

        //When
        paxxoCrawlingService.updateItemsWithTaskExecutor(0, PAGE_LIMIT_800);
    }

    @Test
    public void computeTotalRunningTime_whenMaxThreadCountIs8AndPageLimit800() {
        //Given
        mockPaxxoRepositoryMethods();
        int givenPoolSizeToBeTested = 8;
        TaskExecutor taskExecutor = initializeTaskExecutor(givenPoolSizeToBeTested);
        PaxxoCrawlingService paxxoCrawlingService = new PaxxoCrawlingService(paxxoSaleItemCrawler, paxxoRepository, taskExecutor);

        //When
        paxxoCrawlingService.updateItemsWithTaskExecutor(0, PAGE_LIMIT_800);
    }

    @Test
    public void computeTotalRunningTime_whenMaxThreadCountIs12AndPageLimit800() {
        //Given
        mockPaxxoRepositoryMethods();
        int givenPoolSizeToBeTested = 12;
        TaskExecutor taskExecutor = initializeTaskExecutor(givenPoolSizeToBeTested);
        PaxxoCrawlingService paxxoCrawlingService = new PaxxoCrawlingService(paxxoSaleItemCrawler, paxxoRepository, taskExecutor);

        //When
        paxxoCrawlingService.updateItemsWithTaskExecutor(0, PAGE_LIMIT_800);
    }

    @Test
    public void computeTotalRunningTime_whenMaxThreadCountIs16AndPageLimit800() {
        //Given
        mockPaxxoRepositoryMethods();
        int givenPoolSizeToBeTested = 16;
        TaskExecutor taskExecutor = initializeTaskExecutor(givenPoolSizeToBeTested);
        PaxxoCrawlingService paxxoCrawlingService = new PaxxoCrawlingService(paxxoSaleItemCrawler, paxxoRepository, taskExecutor);

        //When
        paxxoCrawlingService.updateItemsWithTaskExecutor(0, PAGE_LIMIT_800);
    }

    @Test
    public void computeTotalRunningTime_whenMaxThreadCountIs20AndPageLimit800() {
        //Given
        mockPaxxoRepositoryMethods();
        int givenPoolSizeToBeTested = 20;
        TaskExecutor taskExecutor = initializeTaskExecutor(givenPoolSizeToBeTested);
        PaxxoCrawlingService paxxoCrawlingService = new PaxxoCrawlingService(paxxoSaleItemCrawler, paxxoRepository, taskExecutor);

        //When
        paxxoCrawlingService.updateItemsWithTaskExecutor(0, PAGE_LIMIT_800);
    }

    @Test
    public void computeTotalRunningTime_whenMaxThreadCountIs8AndPageLimit1600() {
        //Given
        mockPaxxoRepositoryMethods();
        int givenPoolSizeToBeTested = 8;
        TaskExecutor taskExecutor = initializeTaskExecutor(givenPoolSizeToBeTested);
        PaxxoCrawlingService paxxoCrawlingService = new PaxxoCrawlingService(paxxoSaleItemCrawler, paxxoRepository, taskExecutor);

        //When
        paxxoCrawlingService.updateItemsWithTaskExecutor(0, PAGE_LIMIT_1600);
    }

    @Test
    public void computeTotalRunningTime_whenMaxThreadCountIs16AndPageLimit1600() {
        //Given
        mockPaxxoRepositoryMethods();
        int givenPoolSizeToBeTested = 16;
        TaskExecutor taskExecutor = initializeTaskExecutor(givenPoolSizeToBeTested);
        PaxxoCrawlingService paxxoCrawlingService = new PaxxoCrawlingService(paxxoSaleItemCrawler, paxxoRepository, taskExecutor);

        //When
        paxxoCrawlingService.updateItemsWithTaskExecutor(0, PAGE_LIMIT_1600);
    }


    private void mockPaxxoRepositoryMethods() {
        doNothing().when(paxxoRepository).saveItems(anyCollection());
        doNothing().when(paxxoRepository).saveMakerIndices(anyCollection());
    }

    private ThreadPoolTaskExecutor initializeTaskExecutor(int maxPoolSize) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setCorePoolSize(maxPoolSize);
        taskExecutor.initialize();
        return taskExecutor;
    }
}