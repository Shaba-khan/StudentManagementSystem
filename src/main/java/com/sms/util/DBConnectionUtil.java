package com.sms.util;

import com.sms.exception.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Central place for obtaining JDBC connections.
 * Loads db.properties from the classpath once, registers the driver,
 * and hands out fresh Connections on demand.
 *
 * Environment variables (DB_HOST, DB_NAME, DB_USER, DB_PASSWORD), if present,
 * override the file - this lets the same build run in Codespaces or locally.
 */
public final class DBConnectionUtil {

    private static String url;
    private static String user;
    private static String password;

    // Static initializer: runs once when the class is first loaded.
    static {
        Properties props = new Properties();
        try (InputStream in = DBConnectionUtil.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (in == null) {
                throw new DataAccessException("db.properties not found on classpath");
            }
            props.load(in);

            String driver = props.getProperty("db.driver");
            Class.forName(driver); // register JDBC driver

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            // Optional environment overrides (useful across local vs cloud)
            String envHost = System.getenv("DB_HOST");
            String envName = System.getenv("DB_NAME");
            String envUser = System.getenv("DB_USER");
            String envPass = System.getenv("DB_PASSWORD");

            if (envHost != null && envName != null) {
                url = "jdbc:mysql://" + envHost + ":3306/" + envName
                        + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            }
            if (envUser != null) {
                user = envUser;
            }
            if (envPass != null) {
                password = envPass;
            }

        } catch (IOException e) {
            throw new DataAccessException("Failed to load db.properties", e);
        } catch (ClassNotFoundException e) {
            throw new DataAccessException("JDBC driver class not found", e);
        }
    }

    private DBConnectionUtil() {
        // utility class - no instances
    }

    /**
     * @return a new database connection. Caller is responsible for closing it
     *         (use try-with-resources).
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataAccessException("Unable to open database connection", e);
        }
    }
}
