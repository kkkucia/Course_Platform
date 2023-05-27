package org.example.controllers.reservations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.controllers.MainController;
import org.example.models.courses.Reservation;
import org.example.models.views.AvailableCourse;
import org.example.models.views.CanceledReservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class ReservationsController extends MainController {

    @CrossOrigin
    @GetMapping("/reservations/canceled")
    public String getCanceledReservations() {
        Object[] objects = session.createSQLQuery("SELECT * FROM CANCELED_RESERVATION_VIEW").stream().toArray();
        List<CanceledReservation> canceledReservationList = new ArrayList<>();
        Object[] currObj;
        CanceledReservation canceledReservation;
        for (Object result : objects) {
            currObj = (Object[]) result;
            canceledReservation = new CanceledReservation(currObj);
            canceledReservationList.add(canceledReservation);
        }
        return gson.toJson(canceledReservationList);
    }

    @CrossOrigin
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        Object[] queryResults = session.createQuery("from Reservation").stream().toArray();
        List<Reservation> reservations = new ArrayList<>();
        Arrays.stream(queryResults).forEach(queryResult -> reservations.add((Reservation) queryResult));
        return reservations;
    }

    @CrossOrigin
    @GetMapping("/reservations/users/{userID}")
    public List<Reservation> getReservationsForUser(@PathVariable("userID") long userID) {
        Object[] queryResults = session.createQuery("from Reservation r where r.participant.id=:userID")
                .setParameter("userID", userID)
                .stream()
                .toArray();
        List<Reservation> reservations = new ArrayList<>();
        Arrays.stream(queryResults).forEach(queryResult -> reservations.add((Reservation) queryResult));
        return reservations;
    }

    @CrossOrigin
    @PostMapping("/reservations")
    public ResponseEntity makeReservation(@RequestBody Map<String, Long> json) {
        try {
            Query query = session.createSQLQuery(
                            "CALL make_reservation(:course_id, :participant_id)")
                    .setParameter("course_id", json.get("course_id"))
                    .setParameter("participant_id", json.get("participant_id"));
            System.out.println(query.getResultList());
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        } catch (NegativeArraySizeException ignored) {
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}