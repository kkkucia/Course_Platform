package org.example.models.courses;

import org.example.models.users.Mentor;
import org.example.utils.DbElement;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Course implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private BigDecimal price;
    private Date startDate;
    private Date endDate;
    private int maxNoPlaces;
    private int availablePlaces;
    @ManyToOne
    @JoinColumn(name="CATEGORY_FK")
    private Category category;
    @ManyToMany(mappedBy = "courses")
    private Set<Mentor> mentors;
    public Course(String title, BigDecimal price, Date startDate, Date endDate, int maxNoPlaces, int availablePlaces) {
        this.title = title;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxNoPlaces = maxNoPlaces;
        this.availablePlaces = availablePlaces;
        this.mentors = new HashSet<>();
    }

    public Course() {
    }

    public String getTitle() {
        return title;
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
                ", category= "+ category.getCategoryName() +
                ", mentors= " + mentors.stream().map(Mentor::toString).collect(Collectors.joining(", ")) +
                '}';
    }
}
