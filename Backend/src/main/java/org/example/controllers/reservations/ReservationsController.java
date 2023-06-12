package org.example.controllers.reservations;

import org.example.controllers.MainController;
import org.example.models.courses.Reservation;
import org.example.models.views.reservation.CanceledReservation;
import org.example.models.views.reservation.ReservationFromFunction;
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
    @GetMapping("/reservations/courses")
    public String getReservationsFromCourse(@RequestParam long courseId) {
        Query query = session.createSQLQuery("SELECT * FROM f_reservations_from_course(:courseId)")
                .setParameter("courseId", courseId);
        return prepareQueryWithReservations(query);
    }

    private String prepareQueryWithReservations(Query query) {
        List<ReservationFromFunction> canceledReservationList = new ArrayList<>();
        Object[] currObj;
        ReservationFromFunction reservation;
        try {
            for (Object result : query.getResultList()) {
                currObj = (Object[]) result;
                reservation = new ReservationFromFunction(currObj);
                canceledReservationList.add(reservation);
            }
        } catch (PersistenceException ignored) {
        }
        return gson.toJson(canceledReservationList);
    }

    @CrossOrigin
    @GetMapping("/reservations/unpaid/users")
    public String getUnpaidReservationsForUser(@RequestParam long userId) {
        Query query = session.createSQLQuery("SELECT * FROM f_unpaid_reservations_for_participant(:userId)")
                .setParameter("userId", userId);
        return prepareQueryWithReservations(query);
    }

    @CrossOrigin
    @GetMapping("/reservations/participants")
    public String getReservationsForParticipant(@RequestParam long participantId) {
        Query query = session.createSQLQuery("SELECT * FROM f_reservations_for_participant(:participantId)")
                .setParameter("participantId", participantId);
        return prepareQueryWithReservations(query);
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
    public ResponseEntity<HttpStatus> makeReservation(@RequestBody Map<String, Long> json) {
        try {
            Query query = session.createSQLQuery(
                            "CALL make_reservation(:courseId, :participantId)")
                    .setParameter("courseId", json.get("courseId"))
                    .setParameter("participantId", json.get("participantId"));
            System.out.println(query.getResultList());
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        } catch (NegativeArraySizeException ignored) {
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/reservations/cancel")
    public ResponseEntity<HttpStatus> cancelReservation(@RequestBody Map<String, Long> json){
        try {
            Query query = session.createSQLQuery(
                            "CALL cancel_reservation(:reservationId)")
                    .setParameter("reservationId", json.get("reservationId"));
            System.out.println(query.getResultList());
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        } catch (NegativeArraySizeException ignored) {
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
