package org.example.controllers.users;

import org.example.controllers.MainController;
import org.example.models.courses.Course;
import org.example.models.users.Participant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UsersController extends MainController {

    @CrossOrigin
    @GetMapping("/")
    public String mainPageMessage() {
        return "Welcome";
    }

    @CrossOrigin
    @GetMapping("/participants")
    public List<Participant> getParticipants() {
        Object[] queryResults = session.createQuery("from Participant").stream().toArray();
        List<Participant> participants = new ArrayList<>();
        Arrays.stream(queryResults).forEach(queryResult -> participants.add((Participant) queryResult));
        return participants;
    }
}
