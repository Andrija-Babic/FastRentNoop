package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides connections to the FastRent MySQL database.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class DBConnection {

    private static final String URL =
            "jdbc:mysql://mysql-15ff3dad-adriba001-6b18.h.aivencloud.com:11139/defaultdb"
                    + "?sslMode=REQUIRED";

    private static final String USER =
            System.getenv("AIVEN_DB_USER");

    private static final String PASSWORD =
            System.getenv("AIVEN_DB_PASSWORD");

    /**
     * Creates and returns a database connection.
     *
     * @return the value produced by this operation
    */
    public static Connection connect() throws SQLException {

        if (USER == null || USER.isBlank()) {
            throw new SQLException(
                    "AIVEN_DB_USER environment variable nije postavljen."
            );
        }

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                    "AIVEN_DB_PASSWORD environment variable nije postavljen."
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}
