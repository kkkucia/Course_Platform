package org.example.models.users;

import org.example.models.courses.Reservation;
import org.example.utils.DbElement;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Participant extends User implements DbElement {
    @ManyToMany
    private Set<Reservation> reservations;

    public Participant() {
    }

    public Participant(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
        this.reservations = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Mentor{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", reservations: " + reservations
                .stream()
                .map(reservation -> reservation.getCourse().getTitle() + reservation.getStatus())
                .collect(Collectors.joining(", ")) +
                '}';
    }
}
