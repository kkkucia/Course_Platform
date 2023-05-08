package org.example.models.users;

import org.example.utils.DbElement;

import javax.persistence.Entity;

@Entity
public class Participant extends User implements DbElement {
    public Participant() {
    }

    public Participant(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
