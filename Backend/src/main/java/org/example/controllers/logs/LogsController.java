package org.example.controllers.logs;

import org.example.controllers.MainController;
import org.example.models.others.LogTable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class LogsController extends MainController {

    @CrossOrigin
    @GetMapping("/logs")
    public List<LogTable> getLogs() {
        Object[] queryResults = session.createQuery("from Log_table").stream().toArray();
        List<LogTable> logs = new ArrayList<>();
        Arrays.stream(queryResults).forEach(queryResult -> logs.add((LogTable) queryResult));
        return logs;
    }

    @CrossOrigin
    @GetMapping("/logs/{log_id}")
    public LogTable getLog(@PathVariable("log_id") long logID) {
        Optional queryResults = session.createQuery("from Log_table log where log.id = :log_id")
                .setParameter("log_id", logID)
                .stream()
                .findFirst();
        if (queryResults.isPresent()) {
            return (LogTable) queryResults.get();
        }
        return null;
    }
}
