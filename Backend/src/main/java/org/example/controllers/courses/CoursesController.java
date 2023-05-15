package org.example.controllers.courses;

import org.example.controllers.MainController;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.example.models.courses.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoursesController extends MainController {

    @CrossOrigin
    @GetMapping("/courses/available")
    public Object[] getProcedure() {
        return session.createSQLQuery("SELECT * FROM AVAILABLE_COURSES_VIEW").stream().toArray();
    }

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
}
