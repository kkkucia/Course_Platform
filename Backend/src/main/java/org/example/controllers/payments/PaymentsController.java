package org.example.controllers.payments;

import org.example.connection.DBConnection;
import org.example.controllers.MainController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.Map;

@RestController
public class PaymentsController extends MainController {
    @CrossOrigin
    @PostMapping("/payments/reservations")
    public ResponseEntity<HttpStatus> payForReservation(@RequestBody Map<String, String> json) {
        try {
            Query query = session.createSQLQuery(
                            "CALL PAY_FOR_RESERVATION(:reservationId, :paymentType)")
                    .setParameter("reservationId", Long.parseLong(json.get("reservationId")))
                    .setParameter("paymentType", json.get("paymentType").toUpperCase());
            System.out.println(query.getResultList());
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        } catch (NegativeArraySizeException ignored) {
        }
        session = DBConnection.getSession();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/payments/participants/reservations")
    public ResponseEntity<HttpStatus> payForAllParticipantReservations(@RequestBody Map<String, String> json) {
        try {
            Query query = session.createSQLQuery(
                            "CALL PAY_FOR_ALL_UNPAID_RESERVATIONS(:participantId, :paymentType)")
                    .setParameter("participantId", Long.parseLong(json.get("participantId")))
                    .setParameter("paymentType", json.get("paymentType").toUpperCase());
            System.out.println(query.getResultList());
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        } catch (NegativeArraySizeException ignored) {
        }
        session = DBConnection.getSession();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
