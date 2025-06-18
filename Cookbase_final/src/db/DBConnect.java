package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {
    private static final Logger LOGGER = Logger.getLogger(DBConnect.class.getName());
    private static final String URL = "jdbc:mariadb://localhost:3306/cookbase";
    private static final String USER = "your_user";
    private static final String PASS = "your_password";

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            LOGGER.info("MariaDB JDBC-Driver registriert");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MariaDB JDBC-Driver nicht gefunden. Bitte JAR ins lib-Verzeichnis legen!", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "DB-Verbindung fehlgeschlagen", e);
            return null;
        }
    }
}