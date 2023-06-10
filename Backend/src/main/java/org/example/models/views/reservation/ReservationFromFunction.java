package org.example.models.views.reservation;

import java.math.BigDecimal;

public class ReservationFromFunction extends CanceledReservation {
    private final BigDecimal course_id;

    public ReservationFromFunction(Object[] currObject) {
        super(currObject, (BigDecimal) currObject[3]);
        course_id = (BigDecimal) currObject[3];
    }
}
