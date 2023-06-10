package org.example.models.others;

import org.example.models.courses.Reservation;
import org.example.utils.DbElement;
import org.example.utils.ReservationStatus;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Log_table")
public class LogTable implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    @JoinColumn(name = "RESERVATION_FK")
    private Reservation reservation;
    @Column(name = "log_date")
    private Date logDate;
    private ReservationStatus status;

    public LogTable(Reservation reservation, ReservationStatus status) {
        this.reservation = reservation;
        this.logDate = new Date();
        this.status = status;
    }

    public LogTable() {
    }


    public long getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Date getLogDate() {
        return logDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", reservation=" + reservation.getCourse().getTitle() + " " + reservation.getStatus() +
                ", logDate=" + logDate +
                ", status=" + status +
                '}';
    }
}
