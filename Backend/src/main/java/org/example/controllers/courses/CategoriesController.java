package org.example.controllers.courses;

import org.example.connection.DBConnection;
import org.example.controllers.MainController;
import org.example.models.courses.Category;
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
public class CategoriesController extends MainController {
    @CrossOrigin
    @GetMapping("/categories")
    public List<Category> getCategories() {
        Object[] queryResults = session.createQuery("from Category").stream().toArray();
        List<Category> reservations = new ArrayList<>();
        Arrays.stream(queryResults).forEach(queryResult -> reservations.add((Category) queryResult));
        return reservations;
    }

    @CrossOrigin
    @PostMapping("/categories/courses")
    public ResponseEntity<HttpStatus> addCourseToCategory(@RequestBody Map<String, Long> json) {
        try {
            Query query = session.createSQLQuery(
                            "CALL add_course_to_category(:courseId, :categoryId)")
                    .setParameter("courseId", json.get("courseId"))
                        .setParameter("categoryId", json.get("categoryId"));
            System.out.println(query.getResultList());
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);
        } catch (NegativeArraySizeException ignored) {
        }
        session = DBConnection.getSession();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
