package com.testproject.testproject.run;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private static final Logger log = LoggerFactory.getLogger(RunController.class);
    private final RunRepository runRepository;

    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> findAll() {
        log.info("Fetch all request received");
        return runRepository.findAll();
    }

    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {
        log.info("The run with id {} was requestd ", id);
        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new RunNotFoundException();
        }
        return run.get();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void updateRun(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runRepository.save(run);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void createRun(@Valid @RequestBody Run run) {
        runRepository.save(run);
    }

    @DeleteMapping("/{id}")
    void deleteRunById(@PathVariable Integer id) {
        if (runRepository.findById(id).isEmpty()) {
            throw new RunNotFoundException();
        }
        runRepository.delete(runRepository.findById(id).get());
    }

    @GetMapping("/locations/{location}")
    public List<Run> findAllByLocation(@PathVariable String location) {
        return runRepository.findAllByLocation(location);
    }

}
