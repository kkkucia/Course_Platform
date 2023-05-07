package org.example.models.courses;

import org.example.utils.DbElement;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Invoice implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "transaction_date")
    private Date transactionDate;
    private String type;
    @ManyToOne
    @JoinColumn(name="CONNECTED_RESERVATION_FK")
    private Reservation reservation;

    public Invoice(Date date, String type) {
        this.transactionDate = date;
        this.type = type;
    }

    public Invoice() {
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", date=" + transactionDate +
                ", type='" + type + '\'' +
//                ", reservationId=" + reservation.getId() +
                '}';
    }
}
