package com.example.bankapplication;


import com.example.bankapplication.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml"); // base configuration

            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(LastThreeUpdate.class);
            configuration.addAnnotatedClass(LastThreeTransaction.class);
configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }
}
