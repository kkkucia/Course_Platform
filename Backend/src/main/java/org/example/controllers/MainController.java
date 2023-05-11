package org.example.controllers;

import org.example.connection.DBConnection;
import org.hibernate.Session;

public abstract class MainController {
    protected final Session session;
    public MainController() {
        this.session = DBConnection.getSession();
    }
}
