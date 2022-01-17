package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;


public class Util {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties prop= new Properties();
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/base");
            prop.setProperty("hibernate.connection.username", "root");
            prop.setProperty("hibernate.connection.password", "ghjuf2401");
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

            sessionFactory = new Configuration()
                    .addPackage("com.concretepage.persistence")
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){

        return sessionFactory;
    }// реализуйте настройку соеденения с БД
}
