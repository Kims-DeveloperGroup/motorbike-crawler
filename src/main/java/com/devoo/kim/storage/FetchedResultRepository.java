package com.devoo.kim.storage;

import com.devoo.kim.storage.exception.FailToSaveFetchedResultException;
import crawlercommons.fetcher.FetchedResult;

import java.util.List;

/**
 * Created by rikim on 2017. 7. 16..
 */
public interface FetchedResultRepository {
    void save(List<FetchedResult> fetchedResults) throws FailToSaveFetchedResultException;
    List<FetchedResult> findAll();
}
