package org.example.models.views;

import java.math.BigDecimal;

public class CanceledReservation {
    private BigDecimal id;
    private String title;
    private BigDecimal price;
    private String status;
    private String firstName;
    private String lastName;

    public CanceledReservation(Object[] currObject) {
        this.id = (BigDecimal) currObject[0];
        this.title = (String) currObject[1];
        this.price = (BigDecimal) currObject[2];
        this.status = (String) currObject[3];
        this.firstName = (String) currObject[4];
        this.lastName = (String) currObject[5];
    }
}
