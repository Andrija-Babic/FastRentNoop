package dao.client;

import database.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provides data-access operations for client schema data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ClientSchemaDAO {

    /**
     * Initializes the data or component required by the application.
     *
     * <p>This method ensures that the client table contains the
     * required username, password and pricing type columns.
     * Existing premium values are migrated to the corresponding
     * pricing type without removing the original premium column.</p>
     */
    public static void initializePasswordColumns() {

        String usernameColumn =
                "ALTER TABLE clients " +
                        "ADD COLUMN username VARCHAR(50) UNIQUE";

        String passwordColumn =
                "ALTER TABLE clients " +
                        "ADD COLUMN password_hash VARCHAR(255)";

        String pricingTypeColumn =
                "ALTER TABLE clients " +
                        "ADD COLUMN pricing_type VARCHAR(30)";

        String migratePricingType =
                "UPDATE clients " +
                        "SET pricing_type = CASE " +
                        "WHEN premium = 1 THEN 'PREMIUM' " +
                        "ELSE 'REGULAR' " +
                        "END " +
                        "WHERE pricing_type IS NULL";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            addColumn(stmt, usernameColumn);
            addColumn(stmt, passwordColumn);
            addColumn(stmt, pricingTypeColumn);

            migratePricingType(stmt);

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

    private static void migratePricingType(
            Statement stmt) {

        try {
            stmt.executeUpdate(
                    "UPDATE clients " +
                            "SET pricing_type = CASE " +
                            "WHEN premium = 1 THEN 'PREMIUM' " +
                            "ELSE 'REGULAR' " +
                            "END " +
                            "WHERE pricing_type IS NULL"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}