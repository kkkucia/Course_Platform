package org.example.controllers.users;

import org.example.controllers.MainController;
import org.example.models.users.Mentor;
import org.example.models.users.Participant;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @CrossOrigin
    @GetMapping("/participants/{participantId}")
    public Participant getParticipant(@PathVariable("participantId") long participantId) {
        Optional queryResults = session.createQuery("from Participant p where p.id = :participantId")
                .setParameter("participantId", participantId)
                .stream()
                .findFirst();
        if (queryResults.isPresent()) {
            return (Participant) queryResults.get();
        }
        return null;
    }

    @CrossOrigin
    @GetMapping("/mentors")
    public List<Mentor> getMentors() {
        Object[] queryResults = session.createQuery("from Mentor").stream().toArray();
        List<Mentor> mentors = new ArrayList<>();
        Arrays.stream(queryResults).forEach(queryResult -> mentors.add((Mentor) queryResult));
        return mentors;
    }

    @CrossOrigin
    @GetMapping("/mentors/{mentorId}")
    public Mentor getMentor(@PathVariable("mentorId") long mentorId) {
        Optional queryResults = session.createQuery("from Mentor m where m.id = :mentorId")
                .setParameter("mentorId", mentorId)
                .stream()
                .findFirst();
        if (queryResults.isPresent()) {
            return (Mentor) queryResults.get();
        }
        return null;
    }
}
