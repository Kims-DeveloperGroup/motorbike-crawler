package com.devoo.kim.storage;

import crawlercommons.fetcher.FetchedResult;

import java.util.List;

/**
 * Created by rikim on 2017. 7. 16..
 */
public interface FetchedResultRepository {
    void save();
    List<FetchedResult> findAll();
}
