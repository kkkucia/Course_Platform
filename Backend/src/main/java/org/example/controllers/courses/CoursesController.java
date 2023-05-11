package org.example.controllers.courses;

import org.example.controllers.MainController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesController extends MainController {

    @GetMapping("/courses/available")
    public Object[] getProcedure() {
        return session.createSQLQuery("SELECT * FROM AVAILABLE_COURSES_VIEW").stream().toArray();
    }

    @GetMapping("/courses/{courseName}")
    public Object[] getCourse(@PathVariable("courseName") String courseName) {
        return session.createQuery("from Course c where c.title=:courseName")
                .setParameter("courseName", courseName).stream().toArray();
    }
}
