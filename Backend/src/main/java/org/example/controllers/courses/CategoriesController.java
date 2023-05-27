package org.example.controllers.courses;

import org.example.controllers.MainController;
import org.example.models.courses.Category;
import org.hibernate.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CategoriesController extends MainController {
    @CrossOrigin
    @GetMapping("/categories")
    public List<Category> getCategories() {
        Object[] queryResults = session.createQuery("from Category").stream().toArray();
        List<Category> reservations = new ArrayList<>();
        Arrays.stream(queryResults).forEach(queryResult -> reservations.add((Category) queryResult));
        return reservations;
//        Query<Category> categories = session.createQuery("from categories_view")
    }
}
