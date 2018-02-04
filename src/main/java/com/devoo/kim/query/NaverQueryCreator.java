package com.devoo.kim.query;

import com.devoo.kim.repository.paxxo.PaxxoModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Component
public class NaverQueryCreator {

    private PaxxoModelRepository modelRepository;

    @Autowired
    public NaverQueryCreator(PaxxoModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    private final Path QUERY_FILE_PATH = Paths.get("naver.query");


    public List<String> getQueries() throws IOException {
        List<String> queries = readWordsFromQueryFile();
        queries.addAll(readModelsFromPaxxo());
        return queries;
    }

    private List<String> readWordsFromQueryFile() throws IOException {
        List<String> queries = Files.readAllLines(QUERY_FILE_PATH);
        return queries;
    }

    private List<String> readModelsFromPaxxo() {
        List<String> names = new LinkedList<>();
        modelRepository.findAll().forEach(paxxoModel -> {
            names.add(paxxoModel.getName());
        });
        return names;
    }
}
