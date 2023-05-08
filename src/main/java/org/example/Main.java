package org.example;

import org.example.models.courses.Course;
import org.example.models.courses.Reservation;
import org.example.utils.ReservationStatus;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.sql.Date;

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
        final Session session = getSession();
        try {

        } finally {
            session.close();
        }
//        EntityManagerFactory emf = Persistence.
//                createEntityManagerFactory("myDatabaseConfig");
//        EntityManager em = emf.createEntityManager();
//        em.close();
    }
}