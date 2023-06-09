package org.example.models.views;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AvailableCourse {
    //    c.ID,
//       c.TITLE,
//       ca.CATEGORY_NAME,
//       c.START_DATE,
//       c.END_DATE,
//       c.AVAILABLE_PLACES,
//       c.MAX_NO_PLACES,
//       c.PRICE
    private final BigDecimal id;
    private String title;
    private String categoryName;
    private Timestamp startDate;
    private Timestamp endDate;
    private BigDecimal availablePlaces;
    private BigDecimal maxPlaces;
    private BigDecimal price;

    public AvailableCourse(Object[] currObj) {
//        (BigDecimal) currObj[0], (String) currObj[1], (String) currObj[2], (Timestamp) currObj[3],
//        (Timestamp) currObj[4], (BigDecimal) currObj[5], (BigDecimal) currObj[6], (BigDecimal) currObj[7]
        this.id = (BigDecimal) currObj[0];
        this.title = (String) currObj[1];
        this.categoryName = (String) currObj[2];
        this.startDate = (Timestamp) currObj[3];
        this.endDate = (Timestamp) currObj[4];
        this.availablePlaces = (BigDecimal) currObj[5];
        this.maxPlaces = (BigDecimal) currObj[6];
        this.price = (BigDecimal) currObj[7];
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "\"id\":" + id +
//                ",\"title\":\"" + title + '\"' +
//                ",\"categoryName\":\"" + categoryName + '\"' +
//                ",\"startDate\":\"" + startDate + '\"' +
//                ",\"endDate\":\"" + endDate + '\"' +
//                ",\"availablePlaces\":" + availablePlaces +
//                ",\"maxPlaces\":" + maxPlaces +
//                ",\"price\":" + price +
//                '}';
//    }
}
