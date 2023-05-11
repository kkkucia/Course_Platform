package org.example.connection;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

public class DBConnection {
    private static SessionFactory ourSessionFactory;

    private static void runSession() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        if (ourSessionFactory == null) {
            runSession();
        }
        return ourSessionFactory.openSession();
    }

}
