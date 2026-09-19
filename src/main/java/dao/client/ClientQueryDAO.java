package dao.client;

import database.DBConnection;
import model.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides data-access operations for clientquery data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ClientQueryDAO {

    /**
     * Finds and returns matching all data.
     *
     * @return the value produced by this operation
    */
    public static List<Client> findAll() {
        List<Client> clients = new ArrayList<>();

        String sql = "SELECT * FROM clients ORDER BY id";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clients.add(ClientMapper.fromResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }

    /**
     * Finds and returns matching byid data.
     *
     * @param clientId supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Client findById(int clientId) {
        String sql = "SELECT * FROM clients WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return ClientMapper.fromResultSet(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finds and returns matching byfullname data.
     *
     * @param fullName supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Client findByFullName(String fullName) {
        String[] parts = fullName.trim().split("\\s+");

        if (parts.length < 2) {
            return null;
        }

        String firstName = parts[0];
        String lastName = parts[parts.length - 1];

        String sql =
                "SELECT * FROM clients " +
                        "WHERE first_name = ? AND last_name = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return ClientMapper.fromResultSet(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
