package com.devoo.kim.util;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by rikim on 2017. 7. 15..
 */
@Service
public class FetcherUrls {

    public List<String> getAll() throws IOException {
        return Files.readAllLines(Paths.get("fetch-urls.txt"));
    }
}
