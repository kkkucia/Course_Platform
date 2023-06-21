package org.example.controllers;

import com.google.gson.Gson;
import org.example.connection.DBConnection;
import org.hibernate.Session;

public abstract class MainController {
    protected Session session;
    protected final Gson gson;

    public MainController() {
        this.session = DBConnection.getSession();
        this.gson = new Gson();
    }
}
