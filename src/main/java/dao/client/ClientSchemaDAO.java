package dao.client;

import database.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provides data-access operations for clientschema data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ClientSchemaDAO {

    /**
     * Initializes the data or component required by the application.
    */
    public static void initializePasswordColumns() {

        String usernameColumn =
                "ALTER TABLE clients " +
                        "ADD COLUMN username VARCHAR(50) UNIQUE";

        String passwordColumn =
                "ALTER TABLE clients " +
                        "ADD COLUMN password_hash VARCHAR(255)";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            addColumn(stmt, usernameColumn);
            addColumn(stmt, passwordColumn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addColumn(
            Statement stmt,
            String sql) {

        try {
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            if (!e.getMessage()
                    .toLowerCase()
                    .contains("duplicate")) {

                e.printStackTrace();
            }
        }
    }
}
