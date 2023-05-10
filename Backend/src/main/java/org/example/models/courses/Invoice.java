package org.example.models.courses;

import org.example.utils.DbElement;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Invoice implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "transaction_date")
    private Date transactionDate;
    private String type;
    private BigDecimal allPrice;

    @OneToMany
    @JoinColumn(name = "CONNECTED_RESERVATION_FK")
    private Set<Reservation> reservations;

    public Invoice(Date date, String type, BigDecimal allPrice) {
        this.transactionDate = date;
        this.type = type;
        this.allPrice = allPrice;
//        this.reservations = new HashSet<>();
    }

    public Invoice() {
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", date=" + transactionDate +
                ", type='" + type +
                ", allPrice='" + allPrice.toString() +
//                ", reservations= " + reservations.stream().map(Reservation::toString).collect((Collectors.joining(", "))) + '\'' +
                '}';
    }
}
