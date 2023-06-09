package org.example.controllers.courses;

import org.example.controllers.MainController;
import org.example.models.courses.Course;
import org.example.models.users.Mentor;
import org.example.models.users.Participant;
import org.example.models.views.AvailableCourse;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

@RestController
public class CoursesController extends MainController {

    @CrossOrigin
    @GetMapping("/courses/available")
    public String getAvailableCourses() {
        Query query = session.createSQLQuery("SELECT * FROM AVAILABLE_COURSES_VIEW");
        return returnPreparedAvailableCourses(query);
    }

    @CrossOrigin
    @GetMapping("/courses/available/between")
    public String getAvailableCoursesBetweenDates(@RequestBody Map<String, Date> dateMap) {
        Query query = session.createSQLQuery("SELECT * FROM F_AVAILABLE_COURSES_ON_TIME(:startDate,:endDate)")
                .setParameter("startDate", dateMap.get("startDate"))
                .setParameter("endDate", dateMap.get("endDate"));
        return returnPreparedAvailableCourses(query);
    }

    @CrossOrigin
    @GetMapping("/courses/categories/available/between")
    public String getAvailableCoursesBetweenDatesByCategory(@RequestBody Map<String, String> inputMap) {
        Query query = session.createSQLQuery("SELECT * FROM f_available_courses_by_category_on_time(:startDate,:endDate, :category_id)")
                .setParameter("startDate", Date.valueOf(inputMap.get("startDate")))
                .setParameter("endDate", Date.valueOf(inputMap.get("endDate")))
                .setParameter("category_id", Integer.parseInt(inputMap.get("category_id")));
        return returnPreparedAvailableCourses(query);
    }

    private String returnPreparedAvailableCourses(Query query) {
        List<AvailableCourse> availableCourses = new ArrayList<>();
        Object[] currObj;
        AvailableCourse availableCourse;
        try {
            for (Object result : query.getResultList()) {
                currObj = (Object[]) result;
                availableCourse = new AvailableCourse(currObj);
                availableCourses.add(availableCourse);
            }
        } catch (PersistenceException ignored) {
        }
        return gson.toJson(availableCourses);
    }

    @CrossOrigin
    @GetMapping("/courses/participants")
    public String getParticipantsForCourse(@RequestBody Map<String, Integer> inputMap) {
        Query query = session.createSQLQuery("SELECT * FROM f_participants_from_course(:course_id)")
                .setParameter("course_id", inputMap.get("course_id"));
        List<Participant> participants = new ArrayList<>();
        Object[] currObj;
        Participant participant;
        try {
            for (Object result : query.getResultList()) {
                currObj = (Object[]) result;
                participant = new Participant(currObj);
                participants.add(participant);
            }
        } catch (PersistenceException ignored) {
        }
        return gson.toJson(participants);
    }

    @CrossOrigin
    @GetMapping("/courses/mentors")
    public String getMentorsForCourse(@RequestBody Map<String, Integer> inputMap) {
        Query query = session.createSQLQuery("SELECT * FROM f_mentors_from_course(:course_id)")
                .setParameter("course_id", inputMap.get("course_id"));
        List<Mentor> mentors = new ArrayList<>();
        Object[] currObj;
        Mentor mentor;
        try {
            for (Object result : query.getResultList()) {
                currObj = (Object[]) result;
                mentor = new Mentor(currObj);
                mentors.add(mentor);
            }
        } catch (PersistenceException ignored) {
        }
        return gson.toJson(mentors);
    }

    @CrossOrigin
    @GetMapping("/courses/{courseName}")
    public Course getCourseByName(@PathVariable("courseName") String courseName) {
        Optional queryResult = session.createQuery("from Course c where c.title=:courseName")
                .setParameter("courseName", courseName).stream().findFirst();
        if (queryResult.isPresent()) {
            return (Course) queryResult.get();
        }
        return null;
    }

    @CrossOrigin
    @GetMapping("/courses/id/{courseId}")
    public Course getCourseById(@PathVariable("courseId") long courseId) {
        Optional queryResult = session.createQuery("from Course c where c.id=:courseId")
                .setParameter("courseId", courseId).stream().findFirst();
        if (queryResult.isPresent()) {
            return (Course) queryResult.get();
        }
        return null;
    }

    @CrossOrigin
    @GetMapping("/courses")
    public List<Course> getCourses() {
        Object[] queryResults = session.createQuery("from Course").stream().toArray();
        List<Course> courses = new ArrayList<>();
        Arrays.stream(queryResults).forEach(queryResult -> courses.add((Course) queryResult));
        return courses;
    }

    @CrossOrigin
    @PostMapping("/courses")
    public ResponseEntity addCourse(@RequestBody Course course) {
//        System.out.println(course.getStartDate());
//        System.out.println(course.getEndDate());
//        System.out.println(course.getMaxNoPlaces());
//        System.out.println(course.getAvailablePlaces());
        Transaction tx = session.beginTransaction();
        session.save(course);
        tx.commit();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/courses/mentors")
    public ResponseEntity addMentorToCourse(@RequestBody Map<String, Long> json) {
        try {
            Query query = session.createSQLQuery(
                            "CALL add_mentor_to_course(:course_id, :mentor_id)")
                    .setParameter("course_id", json.get("course_id"))
                    .setParameter("mentor_id", json.get("mentor_id"));
            System.out.println(query.getResultList());
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        } catch (NegativeArraySizeException ignored) {
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
