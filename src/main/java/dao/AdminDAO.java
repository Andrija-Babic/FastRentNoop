package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides data-access operations for the administrator account.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class AdminDAO {

    /**
     * Returns the passwordhash.
     *
     * @param username supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static String getPasswordHash(String username) {

        String sql =
                "SELECT password_hash " +
                        "FROM admins " +
                        "WHERE username = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password_hash");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates the password data.
     *
     * @param username supplied value used by this operation
     * @param passwordHash supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static boolean updatePassword(
            String username,
            String passwordHash) {

        String sql =
                "UPDATE admins " +
                        "SET password_hash = ? " +
                        "WHERE username = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, passwordHash);
            ps.setString(2, username);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
