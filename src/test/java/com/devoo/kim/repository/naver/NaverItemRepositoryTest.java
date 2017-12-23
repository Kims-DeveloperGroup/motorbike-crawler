package com.devoo.kim.repository.naver;

import com.devoo.kim.domain.naver.NaverItem;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverItemRepositoryTest {
    @Autowired
    private NaverItemRepository naverItemRepository;

    @Before
    public void cleanUpTestData() {
        naverItemRepository.deleteAll();
    }

    @Test
    public void shouldBeItemSaved_whenPaxxoItemsAreGiven() {
        //given
        Collection<NaverItem> expectedItemsToBeSave = new ArrayList<>();
        NaverItem item1 = new NaverItem();

        expectedItemsToBeSave.add(item1);

        //when
        naverItemRepository.save(expectedItemsToBeSave);
        List<NaverItem> actualItemsFromRepository = Lists.newArrayList(naverItemRepository.findAll());

        //then
        assertThat(actualItemsFromRepository).hasSameSizeAs(expectedItemsToBeSave);
    }
}