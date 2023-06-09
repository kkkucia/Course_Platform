package org.example.models.users;

import org.example.models.courses.Course;
import org.example.utils.DbElement;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Mentor extends User implements DbElement {
    @ManyToMany
    private Set<Course> courses;

    public Mentor(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
        this.courses = new HashSet<>();
    }

    public Mentor(Object[] data) {
        super(((BigDecimal) data[0]).longValue(), (String) data[1], (String) data[2], (String) data[3], (String) data[4]);
    }

    public Mentor() {
    }

    @Override
    public String toString() {
        return "Mentor{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", email='" + getEmail() + '\'' +
                //               ", courses= " + courses.stream().map(Course::toString).collect(Collectors.joining(", ")) + '\'' +
                '}';
    }
}
