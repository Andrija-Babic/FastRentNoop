package dao.client;

import database.DBConnection;
import model.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides data-access operations for client data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ClientDAO {

    /**
     * Creates or stores the data handled by this method.
     *
     * @param client supplied value used by this operation
    */
    public static void insert(Client client) {
        String sql =
                "INSERT INTO clients(first_name, last_name, premium) " +
                        "VALUES(?,?,?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setInt(3, client.isPremium() ? 1 : 0);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        return ClientAuthenticationDAO.insertWithCredentials(
                client, username, passwordHash);
    }

    /**
     * Finds and returns matching all data.
     *
     * @return the value produced by this operation
    */
    public static List<Client> findAll() {
        return ClientQueryDAO.findAll();
    }

    /**
     * Finds and returns matching byid data.
     *
     * @param clientId supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Client findById(int clientId) {
        return ClientQueryDAO.findById(clientId);
    }

    /**
     * Finds and returns matching byfullname data.
     *
     * @param fullName supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Client findByFullName(String fullName) {
        return ClientQueryDAO.findByFullName(fullName);
    }

    /**
     * Finds and returns matching byusername data.
     *
     * @param username supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Client findByUsername(String username) {
        return ClientAuthenticationDAO.findByUsername(username);
    }

    /**
     * Returns the passwordhash.
     *
     * @param username supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static String getPasswordHash(String username) {
        return ClientAuthenticationDAO.getPasswordHash(username);
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

        return ClientAuthenticationDAO.setCredentials(
                clientId, username, passwordHash);
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

        return ClientAuthenticationDAO.setUsername(
                clientId, username);
    }

    /**
     * Updates the  data.
     *
     * @param client supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static boolean update(Client client) {
        String sql =
                "UPDATE clients SET first_name = ?, " +
                        "last_name = ?, premium = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setInt(3, client.isPremium() ? 1 : 0);
            ps.setInt(4, client.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes the  data.
     *
     * @param clientId supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static boolean delete(int clientId) {
        String sql = "DELETE FROM clients WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Initializes the data or component required by the application.
    */
    public static void initializePasswordColumns() {
        ClientSchemaDAO.initializePasswordColumns();
    }
}
