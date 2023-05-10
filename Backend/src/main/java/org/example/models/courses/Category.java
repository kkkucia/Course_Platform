package org.example.models.courses;

import org.example.utils.DbElement;

import javax.persistence.*;

@Entity
public class Category implements DbElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "category_name")
    private String categoryName;
    private String description;

    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
