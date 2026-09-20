package controller;

import database.DBConnection;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents the AdminInitializer component used by the FastRent application.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class AdminInitializer {

    private static final String ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";

    /**
     * Initializes the data or component required by the application.
    */
    public static void initialize() {

        String createTableSql = """
                CREATE TABLE IF NOT EXISTS admins (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50) NOT NULL UNIQUE,
                    password_hash VARCHAR(255) NOT NULL
                )
                """;

        String checkAdminSql =
                "SELECT id FROM admins WHERE username = ?";

        String insertAdminSql =
                "INSERT INTO admins "
                        + "(username, password_hash) "
                        + "VALUES (?, ?)";

        try (Connection conn =
                     DBConnection.connect();
             Statement stmt =
                     conn.createStatement()) {

            stmt.executeUpdate(createTableSql);

            if (adminExists(
                    conn,
                    checkAdminSql
            )) {
                return;
            }

            createAdmin(
                    conn,
                    insertAdminSql
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private static boolean adminExists(
            Connection conn,
            String sql) throws SQLException {

        try (PreparedStatement check =
                     conn.prepareStatement(sql)) {

            check.setString(
                    1,
                    ADMIN_USERNAME
            );

            try (ResultSet rs =
                         check.executeQuery()) {

                return rs.next();
            }
        }
    }

    private static void createAdmin(
            Connection conn,
            String sql) throws SQLException {

        String passwordHash =
                PasswordUtil.hashPassword(
                        DEFAULT_ADMIN_PASSWORD
                );

        try (PreparedStatement insert =
                     conn.prepareStatement(sql)) {

            insert.setString(
                    1,
                    ADMIN_USERNAME
            );

            insert.setString(
                    2,
                    passwordHash
            );

            insert.executeUpdate();
        }
    }
}
