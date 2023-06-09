package org.example;

import org.example.connection.DBConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DBConnection.getSession().close();
            System.out.println("Session closed");
        }, "Close session"));
    }
}
