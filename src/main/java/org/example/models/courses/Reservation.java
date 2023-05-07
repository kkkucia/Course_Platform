package org.example.models.courses;

import org.example.models.users.Participant;
import org.example.utils.DbElement;
import org.example.utils.ReservationStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Reservation implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private ReservationStatus status;
    @ManyToMany(mappedBy = "reservations")
    private Set<Participant> participants;
    @ManyToOne
    @JoinColumn(name = "COURSE_FK")
    private Course course;

    public Reservation() {
    }

    public Reservation(ReservationStatus status, Course course) {
        this.status = status;
        this.participants = new HashSet<>();
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", status=" + status +
                ", participants=" + participants.stream()
                .map(participant -> participant.getFirstName() + " " + participant.getLastName())
                .collect(Collectors.joining(", ")) +
                ", course=" + course.getTitle() +
                '}';
    }
}
