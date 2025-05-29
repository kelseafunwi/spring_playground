package com.testproject.testproject.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);

    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("SELECT * FROM RUN").query(Run.class).list();
    }

    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("SELECT id, title, started_on, completed_on, miles, location FROM RUN WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO RUN (id,title,started_on,completed_on,miles,location) VALUES (?,?,?,?,?,?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();

        Assert.state(updated == 1, "Failed to Create Run " + run.title());
    }

    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE RUN SET title = ?, started_on = ?, completed_on = ?, miles = ? where id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), id))
                .update();

        Assert.state(updated == 1, "Failed to update Run " + run.title());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM RUN WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete Run " + id);
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public Integer count() {
        return jdbcClient.sql("SELECT count(*) FROM RUN")
                .query(Integer.class)
                .single();
    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("SELECT * FROM RUN WHERE location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }
}