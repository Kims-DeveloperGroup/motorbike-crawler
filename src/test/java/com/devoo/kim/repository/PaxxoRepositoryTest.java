package com.devoo.kim.repository;

import com.devoo.kim.config.ElasticSearchConfig;
import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.repository.paxxo.PaxxoItemRepository;
import com.devoo.kim.repository.paxxo.PaxxoMakerIndexRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PaxxoRepository.class, ElasticSearchConfig.class,
        PaxxoItemRepository.class, PaxxoMakerIndexRepository.class})
public class PaxxoRepositoryTest {
    @Autowired
    private PaxxoRepository paxxoRepository;

    @Autowired
    PaxxoMakerIndexRepository makerIndexRepository;

    @Test
    public void shouldBeMakerIndicesSaved_whenMakerIndicesAreGiven() {
        //given
        Collection<Maker> makerIndices = new ArrayList<>();
        Maker maker1 = new Maker(1, "makerName1");
        Maker maker2 = new Maker(2, "makerName2");
        makerIndices.add(maker1);
        makerIndices.add(maker2);

        //when
        paxxoRepository.saveMakerIndices(makerIndices);
        List<Maker> indicesFrom = Lists.newArrayList(makerIndexRepository.findAll());

        //then
        Assertions.assertThat(indicesFrom).size();
    }

    @After
    public void deleteAllTestData() {
        makerIndexRepository.deleteAll();
    }
}