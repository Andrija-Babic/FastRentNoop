package dao.vehicle;

import database.DBConnection;
import model.entity.Vehicle;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides data-access operations for vehiclequery data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class VehicleQueryDAO {

    /**
     * Finds and returns matching all data.
     *
     * @return the value produced by this operation
    */
    public static List<Vehicle> findAll() {
        List<Vehicle> vehicles = new ArrayList<>();

        String sql = "SELECT * FROM vehicles";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vehicles.add(createVehicle(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    /**
     * Returns whether vehicleavailable.
     *
     * @param vehicleId supplied value used by this operation
     * @param from supplied value used by this operation
     * @param to supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static boolean isVehicleAvailable(
            int vehicleId,
            LocalDate from,
            LocalDate to)
            throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM reservations
            WHERE vehicle_id = ?
              AND status = 'Potvrđeno'
              AND from_date < ?
              AND to_date > ?
        """;

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setInt(1, vehicleId);
            ps.setDate(2, Date.valueOf(to));
            ps.setDate(3, Date.valueOf(from));

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) == 0;
            }
        }
    }

    private static Vehicle createVehicle(
            ResultSet rs) throws SQLException {

        return new Vehicle(
                rs.getInt("id"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getDouble("price"),
                rs.getString("status")
        );
    }
}
