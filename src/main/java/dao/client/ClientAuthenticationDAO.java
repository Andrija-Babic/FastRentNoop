package dao.client;

import database.DBConnection;
import model.entity.Client;

import java.sql.*;

/**
 * Provides data-access operations for client authentication data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ClientAuthenticationDAO {

    /**
     * Creates or stores the data handled by this method.
     *
     * @param client supplied value used by this operation
     * @param username supplied value used by this operation
     * @param passwordHash supplied value used by this operation
     *
     * @return the value produced by this operation
     */
    public static boolean insertWithCredentials(
            Client client,
            String username,
            String passwordHash) {

        String sql =
                "INSERT INTO clients " +
                        "(first_name, last_name, premium, pricing_type, " +
                        "username, password_hash) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setInt(3, client.isPremium() ? 1 : 0);
            ps.setString(4, client.getPricingType().name());
            ps.setString(5, username);
            ps.setString(6, passwordHash);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    client.setId(keys.getInt(1));
                }
            }

            client.setUsername(username);
            client.setPasswordHash(passwordHash);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Finds and returns matching byusername data.
     *
     * @param username supplied value used by this operation
     *
     * @return the value produced by this operation
     */
    public static Client findByUsername(String username) {
        String sql =
                "SELECT * FROM clients WHERE username = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return ClientMapper.fromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns the passwordhash.
     *
     * @param username supplied value used by this operation
     *
     * @return the value produced by this operation
     */
    public static String getPasswordHash(String username) {
        String sql =
                "SELECT password_hash FROM clients WHERE username = ?";

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
     * Sets the credentials.
     *
     * @param clientId supplied value used by this operation
     * @param username supplied value used by this operation
     * @param passwordHash supplied value used by this operation
     *
     * @return the value produced by this operation
     */
    public static boolean setCredentials(
            int clientId,
            String username,
            String passwordHash) {

        String sql =
                "UPDATE clients SET username = ?, " +
                        "password_hash = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setInt(3, clientId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets the username.
     *
     * @param clientId supplied value used by this operation
     * @param username supplied value used by this operation
     *
     * @return the value produced by this operation
     */
    public static boolean setUsername(
            int clientId,
            String username) {

        String sql =
                "UPDATE clients SET username = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, clientId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}