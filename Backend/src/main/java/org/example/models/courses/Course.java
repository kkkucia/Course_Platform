package org.example.models.courses;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.models.users.Mentor;
import org.example.utils.DbElement;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Course implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "max_no_places")
    private int maxNoPlaces;
    @Column(name = "available_places")
    private int availablePlaces;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_FK")
    private Category category;
    @ManyToMany(mappedBy = "courses")
    private Set<Mentor> mentors;

    public Course(String title, BigDecimal price, Date startDate, Date endDate, int maxNoPlaces, int availablePlaces, Category category) {
        this.title = title;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxNoPlaces = maxNoPlaces;
        this.availablePlaces = availablePlaces;
        this.category = category;
//      this.mentors = new HashSet<>();
    }

    public Course() {
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        long timestamp = startDate.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(timestamp));
        try {
            return new java.sql.Date(dateFormat.parse(dateFormat.format(timestamp)).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Date getEndDate() {
        return endDate;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxNoPlaces=" + maxNoPlaces +
                ", availablePlaces=" + availablePlaces +
                ", category= " + category.getCategoryName() +
//                ", mentors= " + mentors.stream().map(Mentor::toString).collect(Collectors.joining(", ")) + '\'' +
                '}';
    }
}
