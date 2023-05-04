package org.example.models.others;

import org.example.models.courses.Reservation;
import org.example.utils.DbElement;
import org.example.utils.ReservationStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Log implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "RESERVATION_FK")
    private Reservation reservation;
    private Date log_date;
    private ReservationStatus status;

    public Log(Reservation reservation, ReservationStatus status) {
        this.reservation = reservation;
        this.log_date = new Date();
        this.status = status;
    }

    public Log() {
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", reservation=" + reservation.getCourse().getTitle()+" "+reservation.getStatus() +
                ", log_date=" + log_date +
                ", status=" + status +
                '}';
    }
}
