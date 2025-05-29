package com.testproject.testproject.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
    private final ObjectMapper objectMapper;

    private final RunRepository runRepository;

    public RunJsonDataLoader(ObjectMapper objectMapper, RunRepository runRepository) {
        this.objectMapper = objectMapper;
        this.runRepository = runRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.runRepository.count() == 0) {
            try (InputStream stream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                Runs allRuns = objectMapper.readValue(stream, Runs.class);
                log.info("Reading {} runs from json data and saving to database", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs());
            } catch (IOException ioException) {
                throw new RuntimeException("Failed to read json  data", ioException);
            }
        } else {
            log.info("Not Loading runs from json because database is not empty");
        }
    }
}
