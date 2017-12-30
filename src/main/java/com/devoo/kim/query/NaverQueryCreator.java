package com.devoo.kim.query;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class NaverQueryCreator {

    private final Path QUERY_FILE_PATH = Paths.get("naver.query");


    public List<String> getQueries() throws IOException {
        return readWordsFromQueryFile();
    }

    private List<String> readWordsFromQueryFile() throws IOException {
        List<String> queries = Files.readAllLines(QUERY_FILE_PATH);
        return queries;
    }
}
