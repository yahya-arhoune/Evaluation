package ma.projet2.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ma.projet2.classes.*; // Import all your entity classes for Exercice 2

public class HibernateUtil2 {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Force load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Configuration configuration = new Configuration();
            // Configure properties directly for simplicity, reading from application2.properties is more robust
            // For this example, ensure you update these values to match your database settings
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/projet_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "Yahya123@@");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update"); // Use 'update' to not drop the database on every run

            // Add annotated entity classes for Exercice 2
            configuration.addAnnotatedClass(ChefProjet.class);
            configuration.addAnnotatedClass(Projet.class);
            configuration.addAnnotatedClass(Employe.class);
            configuration.addAnnotatedClass(Tache.class);
            configuration.addAnnotatedClass(EmployeTache.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            getSessionFactory().close();
        }
    }
}