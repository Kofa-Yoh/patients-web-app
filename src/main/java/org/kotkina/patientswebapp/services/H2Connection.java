package org.kotkina.patientswebapp.services;

import org.kotkina.patientswebapp.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class H2Connection {

    private static Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(H2Connection.class);

    private static final List<String> INIT_SQLS = List.of(
            "CREATE TABLE IF NOT EXISTS patient (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "surname VARCHAR(255) NOT NULL, " +
                    "firstname VARCHAR(255) NOT NULL, " +
                    "patronymic VARCHAR(255) NOT NULL, " +
                    "birthdate DATE NOT NULL, " +
                    "gender NUMBER(1), " +
                    "snils VARCHAR(11) NOT NULL)"
    );

    public H2Connection() {
        openConnection();
        executeInitSql();
    }

    public static void openConnection() {
        if (connection != null) return;

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./patientsdb", "admin", "admin");
            LOGGER.info("Database connection: SUCCESS.");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.debug("StackTrace: ", e);
            LOGGER.error("Database connection: FAIL.");
        }
    }

    private void executeInitSql() {
        try (Statement statement = connection.createStatement()) {
            INIT_SQLS.forEach(sql -> {
                try {
                    statement.execute(sql);
                } catch (SQLException e) {
                    LOGGER.debug("StackTrace: ", e);
                    throw new DatabaseException(e.getMessage());
                }
            });
        } catch (SQLException e) {
            LOGGER.debug("StackTrace: ", e);
            throw new DatabaseException(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
