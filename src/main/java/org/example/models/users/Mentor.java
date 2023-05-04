package org.example.models.users;

import org.example.models.courses.Course;
import org.example.utils.DbElement;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Mentor extends User implements DbElement {
    @ManyToMany
    private Set<Course> courses;

    public Mentor(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
        this.courses = new HashSet<>();
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
                '}';
    }
}
