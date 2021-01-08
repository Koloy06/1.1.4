package jm.task.core.jdbc.util;

import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    // Connect to MySQL
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/MyBase?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    public static Connection getMySQLConnection() {
        Connection connection;


        connection = getMySQLConnection(URL);

        return connection;
    }

    public static Connection getMySQLConnection(String URL){
        // Declare the class Driver for MySQL DB
        // This is necessary with Java 5 (or older)
        // Java6 (or newer) automatically find the appropriate driver.
        // If you use Java> 5, then this line is not needed.
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME,
                    PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    private static final SessionFactory SESSION_FACTORY;
    static {
        SESSION_FACTORY = new Configuration()
                .addProperties(getHibernateProperties())
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    private Util() {
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
    public static void closeSessionFactory() {
        if (SESSION_FACTORY != null) {
            SESSION_FACTORY.close();
        }
    }
    private static Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", DRIVER_CLASS_NAME);
        properties.put("hibernate.connection.url", URL);
        properties.put("hibernate.connection.username", USERNAME);
        properties.put("hibernate.connection.password", PASSWORD);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.connection.pool_size", 8);
        properties.put("hibernate.current-session_context_class", "thread");
        properties.put("hibernate.hbm2ddl.auto", "none");
        return properties;
    }
}
