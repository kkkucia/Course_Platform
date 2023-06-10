package org.example.models.views.reservation;

import java.math.BigDecimal;

public class CanceledReservation {
    private final BigDecimal id;
    private String title;
    private BigDecimal price;
    private BigDecimal status;
    private final String firstName;
    private final String lastName;

    public CanceledReservation(Object[] currObject) {
        this.id = (BigDecimal) currObject[0];
        this.title = (String) currObject[1];
        this.price = (BigDecimal) currObject[2];
        this.status = (BigDecimal) currObject[3];
        this.firstName = (String) currObject[4];
        this.lastName = (String) currObject[5];
    }

    public CanceledReservation(Object[] objectFromFunction, BigDecimal course_id) {
        this.id = (BigDecimal) objectFromFunction[0];
        this.title = (String) objectFromFunction[4];
        this.price = (BigDecimal) objectFromFunction[2];
        this.status = (BigDecimal) objectFromFunction[1];
        this.firstName = (String) objectFromFunction[5];
        this.lastName = (String) objectFromFunction[6];
    }
}
