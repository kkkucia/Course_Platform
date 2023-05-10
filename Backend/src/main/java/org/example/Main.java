package org.example;

import org.example.models.courses.Category;
import org.example.models.courses.Course;
import org.example.models.courses.Reservation;
import org.example.models.users.Mentor;
import org.example.models.users.Participant;
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
            Transaction tx = session.beginTransaction();

//            Category category1 = new Category("Programowanie","Kurs programowania składa się z teoretycznych wykładów oraz praktycznych ćwiczeń, podczas których uczestnicy piszą własny kod i rozwiązują zadania programistyczne." );
//            Category category2 = new Category("Historia", "Uczestnicy poznają kluczowe wydarzenia i postaci z przeszłości oraz nauczą się analizować historyczne źródła i interpretować fakty. ");
//            Category category3 = new Category("Geografia", "Uczestnicy poznają różne aspekty geografii, takie jak geografia fizyczna, polityczna czy ekonomiczna. Nauczą się rónież korzystać z narzędzi geograficznych i technologii, takich jak systemy informacji geograficznej.");
//
//            session.save(category1);
//            session.save(category2);
//            session.save(category3);
//
//            Mentor mentor1 = new Mentor("Tomasz", "Nowak", "+48 111 222 333", "tomaszNowak@gmail.com");
//            Mentor mentor2 = new Mentor("Alicja", "Kowalska", "+48 444 555 666", "alicjaKowalska@gmail.com");
//            Mentor mentor3 = new Mentor("Piotr", "Lewandowski", "+48 777 888 999", "piotrLewandowski@gmail.com");
//            Mentor mentor4 = new Mentor("Anna", "Wójcik", "+48 111 333 555", "annaWojcik@gmail.com");
//            Mentor mentor5 = new Mentor("Marek", "Szczepański", "+48 222 444 666", "marekSzczepanski@gmail.com");
//            Mentor mentor6 = new Mentor("Katarzyna", "Dąbrowska", "+48 333 666 999", "katarzynaDabrowska@gmail.com");
//            Mentor mentor7 = new Mentor("Tadeusz", "Mazur", "+48 111 555 999", "tadeuszMazur@gmail.com");
//            Mentor mentor8 = new Mentor("Magdalena", "Witkowska", "+48 555 777 999", "magdalenaWitkowska@gmail.com");
//            Mentor mentor9 = new Mentor("Marcin", "Kaczmarek", "+48 333 777 111", "marcinKaczmarek@gmail.com");
//
//            session.save(mentor1);
//            session.save(mentor2);
//            session.save(mentor3);
//            session.save(mentor4);
//            session.save(mentor5);
//            session.save(mentor6);
//            session.save(mentor7);
//            session.save(mentor8);
//            session.save(mentor9);
//
//            Course course1 = new Course("Java", new BigDecimal(10000), new Date(2023, 5, 15), new Date(2023, 8, 15), 30, 30, category1);
//            Course course4 = new Course("Mobile App Development", new BigDecimal(15000), new Date(2023, 8, 1), new Date(2023, 11, 1), 15, 15, category1);
//            Course course5 = new Course("Artificial Intelligence", new BigDecimal(20000), new Date(2023, 9, 1), new Date(2023, 12, 1), 10, 10, category1);
//            Course course6 = new Course("Blockchain", new BigDecimal(25000), new Date(2023, 10, 1), new Date(2024, 1, 1), 5, 5, category1);
//
//            Course course7 = new Course("Starożytna grecja", new BigDecimal(25000), new Date(2023, 10, 11), new Date(2024, 1, 11), 55, 55, category2);
//            Course course8 = new Course("Szlakiem polskich królów", new BigDecimal(25000), new Date(2024, 10, 1), new Date(2024, 10, 30), 33, 33, category2);
//            Course course9 = new Course("Wyprawy Kolumba", new BigDecimal(25000), new Date(2023, 12, 1), new Date(2023, 12, 30), 22, 22, category2);
//
//            Course course10 = new Course("Azja", new BigDecimal(25000), new Date(2023, 8, 12), new Date(2024, 7, 11), 42, 42, category3);
//            Course course11 = new Course("Wyprawy Kolumba", new BigDecimal(25000), new Date(2023, 9, 13), new Date(2024, 5, 12), 66, 66, category3);
//            Course course12 = new Course("Afryka", new BigDecimal(25000), new Date(2023, 11, 14), new Date(2024, 4, 13), 11, 11, category3);
//
//            session.save(course1);
//            session.save(course4);
//            session.save(course5);
//            session.save(course6);
//            session.save(course7);
//            session.save(course8);
//            session.save(course9);
//            session.save(course10);
//            session.save(course11);
//            session.save(course12);
//
//            Participant participant1 = new Participant("Karolina", "Wajda", "+48 555 333 111", "karolinaWajda@gmail.com");
//            Participant participant2 = new Participant("Jan", "Kowalski", "+48 555 111 222", "jankowalski@gmail.com");
//            Participant participant3 = new Participant("Anna", "Nowak", "+48 555 222 333", "annanowak@hotmail.com");
//            Participant participant4 = new Participant("Tomasz", "Kaminski", "+48 555 444 555", "tomaszkaminski@yahoo.com");
//            Participant participant5 = new Participant("Ewa", "Szymanska", "+48 555 666 777", "ewaszymanska@outlook.com");
//            Participant participant6 = new Participant("Piotr", "Wojcik", "+48 555 888 999", "piotrw@gmail.com");
//            Participant participant7 = new Participant("Iwona", "Kaczmarek", "+48 555 000 111", "iwonakaczmarek@hotmail.com");
//            Participant participant8 = new Participant("Adam", "Lewandowski", "+48 555 222 333", "adamlewandowski@gmail.com");
//            Participant participant9 = new Participant("Magdalena", "Witkowska", "+48 555 444 555", "magdalenawitkowska@yahoo.com");
//            Participant participant10 = new Participant("Krzysztof", "Jaworski", "+48 555 666 777", "krzysztofjaworski@outlook.com");
//
//            session.save(participant1);
//            session.save(participant2);
//            session.save(participant3);
//            session.save(participant4);
//            session.save(participant5);
//            session.save(participant6);
//            session.save(participant7);
//            session.save(participant8);
//            session.save(participant9);
//            session.save(participant10);

            tx.commit();
        } finally {
            session.close();
        }
//        EntityManagerFactory emf = Persistence.
//                createEntityManagerFactory("myDatabaseConfig");
//        EntityManager em = emf.createEntityManager();
//        em.close();
    }
}