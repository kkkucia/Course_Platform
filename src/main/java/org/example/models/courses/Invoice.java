package org.example.models.courses;

import org.example.utils.DbElement;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Invoice implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "transaction_date")
    private Date transactionDate;
    private String type;

    @OneToMany
    @JoinColumn(name = "CONNECTED_RESERVATION_FK")
    private Set<Reservation> reservations;

    public Invoice(Date date, String type) {
        this.transactionDate = date;
        this.type = type;
        this.reservations = new HashSet<>();
    }

    public Invoice() {
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", date=" + transactionDate +
                ", type='" + type +
                ", reservations= " + reservations.stream().map(Reservation::toString).collect((Collectors.joining(", "))) + '\'' +
                '}';
    }
}
