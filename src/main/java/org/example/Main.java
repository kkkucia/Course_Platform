package org.example;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(String[] args) {
//        Connection connection = null;
//        Statement statement = null;
//        try {
//            connection = Connector.getConnection();
//            System.out.println("Connection ready");
//            String test = "SELECT * FROM RESERVATION";
//            statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(test);
//            while (resultSet.next()){
//                System.out.println(resultSet.getString(1));
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
        final Session session = getSession();
        try {
        } finally {
            session.close();
        }

    }
}