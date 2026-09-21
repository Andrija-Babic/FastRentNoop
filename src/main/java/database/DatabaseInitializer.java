package database;

import dao.client.ClientSchemaDAO;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Initializes the database schema and required application data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class DatabaseInitializer {

    /**
     * Initializes the data or component required by the application.
     */
    public static void initialize() {

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS vehicles (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    brand VARCHAR(100),
                    model VARCHAR(100),
                    year INT,
                    price_per_day DOUBLE,
                    status VARCHAR(50)
                )
            """);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ClientSchemaDAO.initializePasswordColumns();
    }
}