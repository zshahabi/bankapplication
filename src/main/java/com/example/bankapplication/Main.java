package com.example.bankapplication;

import com.example.bankapplication.view.ConsoleApp;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.util.Map;

public class Main {


    public static void main(final String[] args) throws Exception {

        ConsoleApp consoleApp = new ConsoleApp();
        consoleApp.run();

    }
}