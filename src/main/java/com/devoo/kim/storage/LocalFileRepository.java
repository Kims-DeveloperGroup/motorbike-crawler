package com.devoo.kim.storage;

import com.devoo.kim.domain.FetchedResultWrapper;
import com.devoo.kim.storage.exception.FailToSaveFetchedResultException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crawlercommons.fetcher.FetchedResult;
import org.apache.directory.api.util.Strings;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rikim on 2017. 7. 16..
 */
@Repository
public class LocalFileRepository implements FetchedResultRepository {
    private Path REPOSITORY_PATH = Paths.get("repo/fetched");
    private String FILE_PREFIX = "repo/fetched/fetched-";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void save(List<FetchedResult> fetchedResults) throws FailToSaveFetchedResultException {
        try {
            String json = objectMapper.writeValueAsString(serialize(fetchedResults));
            Files.createDirectories(REPOSITORY_PATH);
            Path filePath = Files.createFile(Paths.get(FILE_PREFIX +System.currentTimeMillis()));
            Files.write(filePath, Strings.getBytesUtf8(json));
        } catch (IOException e) {
            throw new FailToSaveFetchedResultException();
        }
    }

    private List<FetchedResultWrapper> serialize(List<FetchedResult> fetchedResults) {
        List<FetchedResultWrapper> wrappers = new LinkedList<>();
        for (FetchedResult result : fetchedResults) {
            wrappers.add(new FetchedResultWrapper(result));
        }
        return wrappers;
    }

    @Override
    public List<FetchedResult> findAll() {
        return null;
    }
}
