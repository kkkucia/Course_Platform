package org.example.models.courses;

import org.example.models.users.Participant;
import org.example.utils.DbElement;
import org.example.utils.ReservationStatus;

import javax.persistence.*;


@Entity
public class Reservation implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "PARTICIPANT_FK")
    private Participant participant;
    @ManyToOne
    @JoinColumn(name = "COURSE_FK")
    private Course course;

    public Reservation() {
    }

    public Reservation(ReservationStatus status, Course course, Participant participant) {
        this.status = status;
        this.participant = participant;
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Participant getParticipant() {
        return participant;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", status=" + status +
                ", participant=" + participant.getFirstName() + " " + participant.getLastName() +
                ", course=" + course.getTitle() + '\'' +
                '}';
    }
}
