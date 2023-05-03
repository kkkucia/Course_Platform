package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private final String URL;
    private final String LOGIN;
    private final String PASSWORD;
    private static Connector instance;
    private Connection createdConnection;

//    TODO
//    enter database login and password in constructor
    private Connector() {
        this.createdConnection = null;
        this.URL = "jdbc:oracle:thin:@dbmanage.lab.ii.agh.edu.pl:1521:DBMANAGE";
        this.LOGIN = "BD_412814";
        this.PASSWORD = "nowe_haslo";
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        instance = getInstance();
        if (instance.getCreatedConnection() != null) {
            return instance.getCreatedConnection();
        }
        Class.forName("oracle.jdbc.OracleDriver");
        System.out.println("Class found");
        instance.setCreatedConnection(DriverManager.getConnection(instance.getURL(), instance.getLOGIN(), instance.getPASSWORD()));
        return instance.getCreatedConnection();
    }

    private void setCreatedConnection(Connection createdConnection) {
        this.createdConnection = createdConnection;
    }

    private String getURL() {
        return URL;
    }

    private String getLOGIN() {
        return LOGIN;
    }

    private String getPASSWORD() {
        return PASSWORD;
    }

    private Connection getCreatedConnection() {
        return createdConnection;
    }

    private static Connector getInstance() {
        if (instance != null) {
            return instance;
        }
        return new Connector();
    }
}
