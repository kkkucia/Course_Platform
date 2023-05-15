package org.example.controllers.courses;

import org.example.controllers.MainController;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.models.courses.Course;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoursesController extends MainController {

    @CrossOrigin
    @GetMapping("/courses/available")
    public Object[] getProcedure() {
        return session.createSQLQuery("SELECT * FROM AVAILABLE_COURSES_VIEW").stream().toArray();
    }

    @CrossOrigin
    @GetMapping("/courses/{courseName}")
    public List<Course> getCourse(@PathVariable("courseName") String courseName) {
        Object[] queryResult = session.createQuery("from Course c where c.title=:courseName")
                .setParameter("courseName", courseName).stream().toArray();
        List<Course> courses = new ArrayList<>();
        Course tmpCourse;
        for (Object elem : queryResult) {
            tmpCourse = (Course) elem;
            courses.add(tmpCourse);
        }
        return courses;
    }

    @CrossOrigin
    @GetMapping("/courses")
    public List<Course> getCourses() {
        Object[] queryResult = session.createQuery("from Course").stream().toArray();
        List<Course> courses = new ArrayList<>();
        Course tmpCourse;
        for (Object elem : queryResult) {
            tmpCourse = (Course) elem;
            courses.add(tmpCourse);
        }
        return courses;
    }

    @CrossOrigin
    @PostMapping("/courses")
    public ResponseEntity addCourse(@RequestBody Course course) {
        System.out.println(course.getStartDate());
        System.out.println(course.getEndDate());
        Transaction tx = session.beginTransaction();
        session.save(course);
        tx.commit();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
