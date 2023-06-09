package org.example.models.views;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class InvoiceViewElement {
    private final BigDecimal id;
    private BigDecimal allPrice;
    private Timestamp transactionDate;
    private String firstName;
    private String lastName;

    public InvoiceViewElement(Object[] inputObject) {
        this.id = (BigDecimal) inputObject[0];
        this.allPrice = (BigDecimal) inputObject[1];
        this.transactionDate = (Timestamp) inputObject[2];
        this.firstName = (String) inputObject[3];
        this.lastName = (String) inputObject[4];
    }
}
