package org.example.models.users;

import org.example.utils.DbElement;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Participant extends User implements DbElement {
    public Participant() {
    }

    public Participant(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    public Participant(Object[] data) {
        super(((BigDecimal) data[0]).longValue(), (String) data[1], (String) data[2], (String) data[3], (String) data[4]);
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
