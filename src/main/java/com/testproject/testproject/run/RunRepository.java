package com.testproject.testproject.run;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class RunRepository {

    private final List<Run> runs = new ArrayList<>();

    List<Run> findAll() {
        return runs;
    }

    void createRun(Run run) {
        runs.add(run);
    }

    void updateRun(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        existingRun.ifPresent(value -> runs.set(runs.indexOf(value), run));
    }


    void deleteRunById(Integer id) {
        runs.removeIf(run -> Objects.equals(run.id(), id));
    }

    Optional<Run> findById(Integer id) {
        return runs.stream().filter(run -> Objects.equals(run.id(), id)).findFirst();
    }

    @PostConstruct
    private void init() {
        runs.add(new Run(1, "Monday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 5, Location.INDOOR));
        runs.add(new Run(2, "Tuesday Morning Run", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 2, Location.INDOOR));
    }
}
