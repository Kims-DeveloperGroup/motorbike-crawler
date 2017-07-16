package com.devoo.kim.storage;

import crawlercommons.fetcher.FetchedResult;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by rikim on 2017. 7. 16..
 */
@SpringBootTest(classes = {LocalFileRepository.class})
public class LocalFileRepositoryTest {
    @Autowired
    LocalFileRepository localFileRepository;

    @Ignore
    @Test
    public void shouldWriteFetchedResultInLocalFile() {
    }

}