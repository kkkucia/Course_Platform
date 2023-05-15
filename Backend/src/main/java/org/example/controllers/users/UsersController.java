package org.example.controllers.users;

import org.example.controllers.MainController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController extends MainController {

    @CrossOrigin
    @GetMapping("/")
    public String mainPageMessage() {
        return "Welcome";
    }

    @CrossOrigin
    @GetMapping("/participants")
    public Object[] getCourses() {
        return session.createQuery("from Course").stream().toArray();
    }
}
