package org.example.controllers.courses;

import org.example.controllers.MainController;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursesController extends MainController {

    @CrossOrigin
    @GetMapping("/courses/available")
    public Object[] getProcedure() {
        return session.createSQLQuery("SELECT * FROM AVAILABLE_COURSES_VIEW").stream().toArray();
    }
}
