package com.devoo.kim.query;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NaverQueryCreator {

    private final Path QUERY_FILE_PATH = Paths.get("naver.query");


    public String getQuery() throws IOException {
        StringBuilder queryBuilder = new StringBuilder();
        readWordsFromQueryFile().forEach(query -> {
            queryBuilder.append(query).append(" ");
        });
        return queryBuilder.toString();
    }

    private List<String> readWordsFromQueryFile() throws IOException {
        List<String> queries = Files.readAllLines(QUERY_FILE_PATH);
        return queries;
    }
}
