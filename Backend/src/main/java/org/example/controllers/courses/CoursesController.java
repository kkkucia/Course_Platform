package org.example.controllers.courses;

import org.example.controllers.MainController;
import org.example.models.courses.Course;
import org.example.models.users.Mentor;
import org.example.models.users.Participant;
import org.example.models.views.course.AvailableCourse;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
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
    public String getAvailableCoursesBetweenDates(@RequestParam Map<String, Date> allParams) {
        Query query = session.createSQLQuery("SELECT * FROM F_AVAILABLE_COURSES_ON_TIME(:startDate,:endDate)")
                .setParameter("startDate", allParams.get("startDate"))
                .setParameter("endDate", allParams.get("endDate"));
        return returnPreparedAvailableCourses(query);
    }

    @CrossOrigin
    @GetMapping("/courses/categories/available/between")
    public String getAvailableCoursesBetweenDatesByCategory(@RequestParam Map<String, String> inputMap) {
        Query query = session.createSQLQuery("SELECT * FROM f_available_courses_by_category_on_time(:startDate,:endDate, :category_id)")
                .setParameter("startDate", Date.valueOf(inputMap.get("startDate")))
                .setParameter("endDate", Date.valueOf(inputMap.get("endDate")))
                .setParameter("category_id", Long.parseLong(inputMap.get("categoryId")));
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
    public String getParticipantsForCourse(@RequestParam("courseId") long courseId) {
        Query query = session.createSQLQuery("SELECT * FROM f_participants_from_course(:courseId)")
                .setParameter("courseId", courseId);
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
    public String getMentorsForCourse(@RequestParam long courseId) {
        Query query = session.createSQLQuery("SELECT * FROM f_mentors_from_course(:courseId)")
                .setParameter("courseId", courseId);
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
    public ResponseEntity<HttpStatus> addCourse(@RequestBody Course course) {
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
    public ResponseEntity<HttpStatus> addMentorToCourse(@RequestBody Map<String, Long> json) {
        try {
            Query query = session.createSQLQuery(
                            "CALL add_mentor_to_course(:courseId, :mentorId)")
                    .setParameter("courseId", json.get("courseId"))
                    .setParameter("mentorId", json.get("mentorId"));
            System.out.println(query.getResultList());
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        } catch (NegativeArraySizeException ignored) {
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
