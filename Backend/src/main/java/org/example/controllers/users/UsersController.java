package org.example.controllers.users;

import org.example.controllers.MainController;
import org.example.models.courses.Course;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController extends MainController {
    private Transaction tx;

    @GetMapping("/")
    public String mainPageMessage() {
        return "Welcome";
    }

    @GetMapping("/participants")
    public Object[] getCourses() {
        return session.createQuery("from Course").stream().toArray();
    }

    @GetMapping("/participants/{courseName}")
    public Object[] getCourse(@PathVariable("courseName") String courseName) {
        return session.createQuery("from Course c where c.title=:courseName")
                .setParameter("courseName", courseName).stream().toArray();
    }

    @PostMapping("/participants")
    public ResponseEntity addCourse(@RequestBody Course course) {
        System.out.println(course.getStartDate());
        System.out.println(course.getEndDate());
        tx = session.beginTransaction();
        session.save(course);
        tx.commit();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
